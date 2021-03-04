<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../common/header.jsp"%>
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar-2">
        <div class="wu-toolbar-button">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>

        </div>
        <div class="wu-toolbar-search">

            <label>角色名称：</label><input id="search-name" class="wu-text" style="width:100px">
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="wu-datagrid-2" class="easyui-datagrid" toolbar="#wu-toolbar-2"></table>
</div>
<!-- Begin of easyui-dialog -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="add-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">角色名称:</td>
                <td><input type="text" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写角色名称'" /></td>
            </tr>
            <tr>
                <td align="right">角色备注:</td>
                <td><textarea name="remark" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<!-- 修改窗口 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="edit-form" method="post">
        <input type="hidden" name="id" id="edit-id">
        <table>
            <tr>
                <td width="60" align="right">角色名称:</td>
                <td><input type="text" id="edit-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写角色名称'" /></td>
            </tr>
            <tr>
                <td align="right">角色备注:</td>
                <td><textarea id="edit-remark" name="remark" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
            </tr>
        </table>
    </form>
</div>

<!-- 选择权限弹窗 -->
<div id="select-authority-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:220px;height:450px; padding:10px;">
    <ul id="authority-tree" url="get_all_menu" checkbox="true"></ul>
</div>

</body>
<!-- End of easyui-dialog -->
<script type="text/javascript">
    /**
     * Name 添加记录
     */
    function add(){
        var validate = $("#add-form").form("validate");
        if (!validate){
            $.messager.alert("消息提醒","请检查你输入的数据","warning");
            return;
        }
        var data = $("#add-form").serialize();
        $.ajax({
            url:'../../admin/role/add',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示',data.msg,'info');
                    $('#add-dialog').dialog('close');
                    $('#wu-datagrid-2').datagrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }





    /**
     * Name 修改记录
     */
    function edit(){

        var data = $("#edit-form").serialize();
        $.ajax({
            url:'../../admin/role/edit',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','修改成功！','info');
                    $('#edit-dialog').dialog('close');
                    $('#wu-datagrid-2').datagrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }

    //某个角色已经拥有的权限
    var existAuthority = null;
    function isAdded(row,rows){
        for(var k=0;k<existAuthority.length;k++){
            if(existAuthority[k].menuId == row.id && haveParent(rows,row.parentId)){
                //console.log(existAuthority[k].menuId+'---'+row.parentId);
                return true;
            }
        }
        return false;
    }

    //判断是否有父分类

    function haveParent(rows,parentId){
        for(var i=0; i<rows.length; i++){
            if (rows[i].id == parentId){
                if(rows[i].parentId != 0) return true;
            }
        }
        return false;
    }

    //判断是否有父类
    function exists(rows, parentId){
        for(var i=0; i<rows.length; i++){
            if (rows[i].id == parentId) return true;
        }
        return false;
    }

    //转换原始数据至符合tree的要求
    function convert(rows){
        var nodes = [];
        // get the top level nodes
        //首先获取所有的父分类
        for(var i=0; i<rows.length; i++){
            var row = rows[i];
            if (!exists(rows, row.parentId)){
                nodes.push({
                    id:row.id,
                    text:row.name
                });
            }
        }

        var toDo = [];
        for(var i=0; i<nodes.length; i++){
            toDo.push(nodes[i]);
        }
        while(toDo.length){
            var node = toDo.shift();	// the parent node
            // get the children nodes
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                if (row.parentId == node.id){
                    var child = {id:row.id,text:row.name};
                    if(isAdded(row,rows)){
                        child.checked = true;
                    }
                    if (node.children){
                        node.children.push(child);
                    } else {
                        node.children = [child];
                    }
                    //把刚才创建的孩子再添加到父分类的数组中
                    toDo.push(child);
                }
            }
        }
        return nodes;
    }

    //打开权限选择框
    function selectAuthority(roleId){
        $('#select-authority-dialog').dialog({
            closed: false,
            modal:true,
            title: "选择权限信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function(){
                    var checkedNodes = $("#authority-tree").tree('getChecked');
                    var ids = '';
                    for(var i=0;i<checkedNodes.length;i++){
                        ids += checkedNodes[i].id + ',';
                    }
                    var checkedParentNodes = $("#authority-tree").tree('getChecked', 'indeterminate');
                    for(var i=0;i<checkedParentNodes.length;i++){
                        ids += checkedParentNodes[i].id + ',';
                    }
                    //console.log(ids);
                    if(ids != ''){

                        $.ajax({
                            url:'add_authority',
                            type:"post",
                            data:{ids:ids,roleId:roleId},
                            dataType:'json',
                            success:function(data){
                                if(data.type == 'success'){
                                    $.messager.alert('信息提示','权限编辑成！','info');
                                    $('#select-authority-dialog').dialog('close');
                                }else{
                                    $.messager.alert('信息提示',data.msg,'info');
                                }
                            }
                        });
                    }else{
                        $.messager.alert('信息提示','请至少选择一条权限！','info');
                    }
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#select-authority-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){

                // 首先获取该角色已经拥有的权限
                $.ajax({
                    url:'get_role_authority',
                    data:{roleId:roleId},
                    type:'post',
                    dataType:'json',
                    success:function(data){
                        existAuthority = data;
                        $("#authority-tree").tree({
                            loadFilter: function(rows){
                                return convert(rows);
                            }
                        });
                    }
                });

            }
        });
    }

    /**
     * Name 删除记录
     */
    function remove(){
        var item = $('#wu-datagrid-2').datagrid('getSelected');
        if (!item){
            $.messager.alert("消息提醒","请选择删除的角色","warning");
            return;
        }
        $.messager.confirm('信息提示','确定要删除该记录？', function(result){
            if(result){
                $.ajax({
                    url:'../../admin/role/delete',
                    dataType:'json',
                    type:'post',
                    data:{id:item.id},
                    success:function(data){
                        if(data.type == 'success'){
                            $.messager.alert('信息提示','删除成功！','info');
                            $('#wu-datagrid-2').datagrid('reload');
                        }else{
                            $.messager.alert('信息提示',data.msg,'warning');
                        }
                    }
                });
            }
        });
    }

    /**
     * Name 打开添加窗口
     */
    function openAdd(){
        // $('#add-form').form('clear');
        $('#add-dialog').dialog({
            closed: false,
            modal:true,
            title: "添加角色信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: add
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#add-dialog').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 打开修改窗口
     */
    function openEdit(){
        //$('#edit-form').form('clear');
        var item = $('#wu-datagrid-2').datagrid('getSelected');
        if(item == null || item.length == 0){
            $.messager.alert('信息提示','请选择要修改的数据！','info');
            return;
        }

        $('#edit-dialog').dialog({
            closed: false,
            modal:true,
            title: "修改信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: edit
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#edit-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                $("#edit-id").val(item.id);
                $("#edit-name").val(item.name);

                $("#edit-remark").val(item.remark);
            }
        });
    }


    //搜索按钮监听
    $("#search-btn").click(function (){
        $('#wu-datagrid-2').datagrid('reload',{
            name:$("#search-name").val()
        });
    })


    /**
     * Name 载入数据
     */
    $('#wu-datagrid-2').datagrid({
        url:'../../admin/role/list',
        rownumbers:true,
        singleSelect:true,
        pageSize:20,
        pagination:true,
        multiSort:true,
        fitColumns:true,
        idField:'id',
        treeField:'name',
        fit:true,
        columns:[[
            { field:'chk',checkbox:true},
            { field:'name',title:'角色名称',width:100,sortable:true},
            { field:'remark',title:'角色备注',width:100,sortable:true},
            { field:'icon',title:'权限操作',width:100,formatter:function(value,row,index){
                    var test = '<a class="authority-edit" onclick="selectAuthority('+row.id+')"></a>'
                    return test;
                }},
        ]],
        onLoadSuccess:function(data){
            $('.authority-edit').linkbutton({text:'编辑权限',plain:true,iconCls:'icon-edit'});
        }
    });
</script>