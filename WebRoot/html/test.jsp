<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<HTML>

<HEAD>
    <TITLE> ZTREE DEMO </TITLE>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../css/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="../js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="../js/jquery.ztree.all.min.js"></script>

    <script src="../layui/layui.all.js" charset="utf-8"></script>
    <SCRIPT LANGUAGE="JavaScript">
    	alert(sessionStorage.getItem("sysid"));
        var zTreeObj;
        // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
        var setting = {
            view: {
                dblClickExpand: false,//双击节点时，是否自动展开父节点的标识
                showLine: true,//是否显示节点之间的连线
                fontCss: { 'color': 'black', 'font-weight': 'bold' },//字体样式函数
                selectedMulti: true //设置是否允许同时选中多个节点
            },
            check: {
                chkboxType: { "Y": "ps", "N": "ps" },
                //chkboxType: { "Y": "", "N": "" },
                chkStyle: "checkbox",//复选框类型
                enable: true //每个节点上是否显示 CheckBox
            }, callback: {    //第一步
                // onClick: zTreeOnClick
            }

        };
        var zNodes = [];
        function loaddata() {
            $.ajax({
                type: 'get',
                url: "../roleaccess/gettree",
                dataType: 'json',
                data: {
                    sysModelId: '16'
                },
                success: function (data) {
                    zNodes = data.data;
                    zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                    zTreeObj.expandAll(true);


                    //获取树对象
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    /** 获取所有树节点 */
                    var nodes = treeObj.transformToArray(treeObj.getNodes());
                    // 遍历树节点设置树节点为选中
                    for (var k = 0, length_3 = nodes.length; k < length_3; k++) {
                        if (nodes[k].spread==true) {
                            nodes[k].checked = true;
                            treeObj.updateNode(nodes[k], true);
                        }
                    }


                },
                error: function () {

                },
            });

        }
        function zTreeOnClick(event, treeId, treeNode) {
           // sessionStorage.setItem("sysid","16");
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo"),
                nodes = treeObj.getCheckedNodes(true);
                unnodes=treeObj.getCheckedNodes(false);
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].type == "button") {
                    var object = {
                        id: nodes[i].id,
                        relationshipid: nodes[i].relationshipid
                    }
                    arrbtn.push(object);
                } else if (nodes[i].type == "clumn") {
                    var object = {
                        id: nodes[i].id,
                        relationshipid: nodes[i].relationshipid
                    }
                    arrclumn.push(object);
                }
                // v = nodes[i].name + ",";
                // console.log("节点id:" + nodes[i].id + "节点名称" + v); //获取选中节点的值
            }
            
            for (var i = 0; i < unnodes.length; i++) {
                if (unnodes[i].type == "button") {
                    var object = {
                        id: unnodes[i].id,
                        relationshipid: unnodes[i].relationshipid
                    }
                    unarrbtn.push(object);
                } else if (unnodes[i].type == "clumn") {
                    var object = {
                        id: unnodes[i].id,
                        relationshipid: unnodes[i].relationshipid
                    }
                    unarrclumn.push(object);
                }
                // v = nodes[i].name + ",";
                // console.log("节点id:" + nodes[i].id + "节点名称" + v); //获取选中节点的值
            }
            
            console.log("选中按钮");
            console.log(arrbtn);
            console.log("未选中按钮");
            console.log(unarrbtn);
            console.log("选中lie");
            console.log(arrclumn);
            console.log("未选中lie");
            console.log(unarrclumn);
        }

        function send() {
        	
            $.ajax({
                type: 'post',
                url: "../roleaccess/updateaccess",
                dataType: 'json',
                data: {
                    btns: JSON.stringify(arrbtn),
                    clumns: JSON.stringify(arrclumn),
                    unbtns: JSON.stringify(unarrbtn),
                    unclumns: JSON.stringify(unarrclumn),
                    sysModelId: 16
                },
                success: function (data) {
                    if (data.code == 0) {
                        layer.confirm("修改成功", {
                            icon: 1,
                            btn: ['确定']
                        }, function () {
                        	//按钮数组
					         arrbtn = [];
					         unarrbtn = [];
					        //列数组
					         arrclumn = [];
					         unarrclumn = [];
							loaddata();
                            layer.closeAll();
                        });
                    }
                    else {
                    	//按钮数组
					         arrbtn = [];
					         unarrbtn = [];
					        //列数组
					         arrclumn = [];
					         unarrclumn = [];
                        layer.confirm(data.msg, {
                            icon: 7,
                            btn: ['确定']
                        });
                    }
                },
                error: function () {
                	//按钮数组
					         arrbtn = [];
					         unarrbtn = [];
					        //列数组
					         arrclumn = [];
					         unarrclumn = [];
                    layer.confirm('出现错误，请重试！', {
                        icon: 6,
                        btn: ['确定']
                    });
                },
            });
        }

        layui.use(['form', 'layer'], function () {
            var layer = layui.layer
            var form = layui.form;
            var $ = layui.jquery;
        })

        //按钮数组
        var arrbtn = [];
         var unarrbtn = [];
        //列数组
        var arrclumn = [];
        var unarrclumn = [];

    </SCRIPT>
</HEAD>

<BODY>
    <div>
        <ul id="treeDemo" class="ztree"></ul>
    </div>
    <button onclick="loaddata()">加载数据</button>
    <button onclick="zTreeOnClick();send()">确定</button>
</BODY>

</HTML>