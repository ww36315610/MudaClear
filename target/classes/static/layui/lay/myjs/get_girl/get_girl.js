layui.use(['jquery', 'laydate', 'util', 'layer', 'table', 'form', 'layedit'], function ($, laydate, util, layer, table,
            form, layedit) {
            var rownum = 1;
            var rownumRequest = 1;
            var beforeURL = "/muda/oauths";


            form.verify({
               lengthInput: function(value) {
                    if (value.length < 5) {
                        return '文本框不少于5个字符啊';
                     }
                    if (value.length > 200) {
                        return '文本框不超过500个字符啊';
                    }
                },
               pass: [/(.+){6,12}$/, '密码必须6到12位'],
               repass: function(value) {
                    if ($('#L_pass').val() != $('#L_repass').val()) {
                        return '两次密码不一致';
                    }
                }
            });


            $('#formSubmit').click(function event() {
                form.on('submit(formSubmit)', function (data) {
                    data.field.header = tableIns.config.data;
                    data.field.requestBody = tableRequest.config.data;
                    data.field.oauth = objTable;
                    console.log(data.field);
                    $.ajax({
                       url: '/muda/get_girl/send',
                       type: 'post',
                       dataType: 'json',
                       contentType: 'application/json',
                       data: JSON.stringify(data.field),
                       success: function (dataSuc) {
                           console.log('----------------------------------');
                           console.log(JSON.stringify(dataSuc));
                           $('#responseMsg').val(JSON.stringify(dataSuc));

                          },
                       error:function(dataErr){
                           parent.layer.alert("请求失败");
                           $('#responseMsg').val(dataErr);
//                             $('#responseMsg').val("请检查请求地址|参数|header是否正确");
                          }
                       })
                   return false;
                });
            });

            //header
            var tableIns = table.render({
                elem: '#headerID'
                //              , height: 480
                , data: [
                    {
                        id: 1,
                        "KEY": "Content-Type",
                        "VALUE": "application/x-www-form-urlencoded",
                        memo: ''
                    }
                ],
                page: {},
                cols: [[
                    { type: 'checkbox', fixed: true },
                    { field: 'id', title: 'ID', edit: true, width: 50 },
                    { field: 'KEY', edit: true, title: 'KEY' },
                    { field: 'VALUE', edit: true, title: 'VALUE' },
                    { toolbar: '#table_tool', title: '操作', fixed: 'right', align: 'center', width: 120 }
                ]],
                done: function () {
                    //                    $("[data-field='id']").css('display','none');
                }
            });

            // 工具监听
            table.on('tool(headerID)', function (data) {
                switch (data.event) {
                    case 'del':
                        // data.del();
                        // layer.confirm('玩玩而已不用担心一会还会回来的。', function(index){
                        // obj.del();
                        // layer.close(index);
                        // });
                        // 只做测试数据的删除用，url的删除一般都是发交易然后重载表格就可以了
                        var tableObj = tableIns;
                        var config = tableObj.config;
                        var dataTemp = config.data;
                        var page = config.page;
                        // 得到tr的data-index
                        var trElem = data.tr.first();
                        var index = trElem.data('index');
                        // 计算出在data中的index
                        var dataIndex = index + page.limit * (page.curr - 1);
                        console.log('===' + dataIndex);
                        // 删除对应下标的数据
                        dataTemp.splice(dataIndex, 1);

                        // 新的页数
                        var pageNew = Math.ceil(dataTemp.length / page.limit);

                        // 重新接收reload返回的对象，这个很重要
                        tableIns = table.reload(config.id, $.extend(true, {
                            // 更新数据
                            data: dataTemp
                        }, config.page ? {
                            // 如果删除了数据之后页数变了，打开前一页
                            page: {
                                curr: page.curr > pageNew ? ((page.curr - 1) || 1) : page.curr
                            }
                        } : {}));
                        break;
                    default:
                        break;
                }
            });

            // 新增一条记录
            $('#createNew').click(function () {
                // debugger;
                var lasnum = rownum - 1;
                rownum += 1;
                var tableObj = tableIns;
                var config = tableObj.config;
                var dataTemp = config.data;
                console.log('---' + lasnum);

                //            此处插入的是跟第一行内容相同
                //            lastdata = JSON.parse(JSON.stringify(tableIns.config.data[lasnum]))

                var lastdata = { 'id': 1 }
                lastdata.id = rownum;
                dataTemp.push(lastdata);
                tableIns = table.reload(config.id, $.extend(true, {
                    // 更新数据
                    data: dataTemp,
                }, config.page ? {
                    // 一般新增都是加到最后，所以始终打开最后一页
                    page: {
                        curr: Math.ceil(dataTemp.length / config.page.limit)
                    }
                } : {}));
                return false;
            });

            // 获得表格的所有数据
            $('#getAllData').click(function () {
                layer.alert(JSON.stringify(tableIns.config.data));
                return false;
            });
            var editIndex = layedit.build('requestBody');
            form.verify({
                content: function (value) {
                    layedit.sync(editIndex);
                }
            });


            //requestBody
            var tableRequest = table.render({
                elem: '#paramRequest'
                //                , height: 480
                , data: [
                    {
                        id: 1,
                        "KEY": "",
                        "VALUE": "",
                        memo: ''
                    }
                ],
                page: {},
                cols: [[
                    { type: 'checkbox', fixed: true },
                    { field: 'id_1', title: 'ID', edit: true, width: 50 },
                    { field: 'KEY', edit: true, title: 'KEY' },
                    { field: 'VALUE', edit: true, title: 'VALUE' },
                    { toolbar: '#table_tool_request', title: '操作', fixed: 'right', align: 'center', width: 120 }
                ]],
                done: function () {
                    $("[data-field='id_1']").css('display', 'none');
                }
            });

            // 工具监听
            table.on('tool(paramRequest)', function (data) {
                switch (data.event) {
                    case 'del':
                        // data.del();
                        // layer.confirm('玩玩而已不用担心一会还会回来的。', function(index){
                        // obj.del();
                        // layer.close(index);
                        // });
                        // 只做测试数据的删除用，url的删除一般都是发交易然后重载表格就可以了
                        var tableObj = tableRequest;
                        var config = tableObj.config;
                        var dataTemp = config.data;
                        var page = config.page;
                        // 得到tr的data-index
                        var trElem = data.tr.first();
                        var index = trElem.data('index');
                        // 计算出在data中的index
                        var dataIndex = index + page.limit * (page.curr - 1);
                        console.log('===' + dataIndex);
                        // 删除对应下标的数据
                        dataTemp.splice(dataIndex, 1);

                        // 新的页数
                        var pageNew = Math.ceil(dataTemp.length / page.limit);

                        // 重新接收reload返回的对象，这个很重要
                        tableRequest = table.reload(config.id, $.extend(true, {
                            // 更新数据
                            data: dataTemp
                        }, config.page ? {
                            // 如果删除了数据之后页数变了，打开前一页
                            page: {
                                curr: page.curr > pageNew ? ((page.curr - 1) || 1) : page.curr
                            }
                        } : {}));
                        break;
                    default:
                        break;
                }
            });

            // 新增一条记录
            $('#createNewParam').click(function () {
                // debugger;
                var lasnum = rownumRequest - 1;
                rownumRequest += 1;
                var tableObj = tableRequest;

                var config = tableObj.config;
                var dataTemp = config.data;
                console.log('---' + lasnum);

                //                此处插入的是跟第一行内容相同
                //                lastdata = JSON.parse(JSON.stringify(tableRequest.config.data[lasnum]))

                var lastdata = { 'id': 1 }
                lastdata.id = rownumRequest;
                dataTemp.push(lastdata);
                tableRequest = table.reload(config.id, $.extend(true, {
                    // 更新数据
                    data: dataTemp,
                }, config.page ? {
                    // 一般新增都是加到最后，所以始终打开最后一页
                    page: {
                        curr: Math.ceil(dataTemp.length / config.page.limit)
                    }
                } : {}));
                return false;
            });

            // 获得表格的所有数据
            $('#getAllDataParam').click(function () {
                layer.alert(JSON.stringify(tableRequest.config.data));
                return false;
            });
            var editIndex = layedit.build('header');
            form.verify({
                content: function (value) {
                    layedit.sync(editIndex);
                }
            });


            //radion按钮实现div显示与否
            form.on('radio', function (data) {
                if (data.value == 'body') {
                    console.log('1111');
                    $("#IDRequest1").hide(1000);
                    $("#IDRequest2").show(300);
                } else if (data.value == 'param') {
                    console.log('2222');
                    $("#IDRequest2").hide(1000);
                    $("#IDRequest1").show(500);
                }
            });


            //监听Tab选项卡切换
            //          var tableOauth;
            //          element.on('tab(tabDemo)', function(data){
            //方法级渲染
            var tableOauth = table.render({
                elem: '#table_auth'
                , url: beforeURL + '/get'
                //                    ,toolbar: '#toolbarDemo'
                , title: '用户数据表'
                , request: {
                    pageName: 'pageNum' // 页码的参数名称，默认：page
                    , limitName: 'pageSize' //每页数据量的参数名，默认：limit
                }
                , response: {
                    statusName: 'code', //数据状态的字段名称，默认：code
                    statusCode: 200, //成功的状态码，默认：0
                    msgName: 'msg', //状态信息的字段名称，默认：msgz
                    countName: 'count', //数据总数的字段名称，默认：count
                    dataName: 'data' //数据列表的字段名称，默认：data
                }
                , cols: [
                    [
                        { type: 'radio' }
                        , { field: 'id', title: 'ID', width: 80, sort: true, fixed: true }
                        , { field: 'projectName', title: '项目名称', sort: true, width: 120, edit: true }
                        , { field: 'oauthUrl', title: 'oauth_url', edit: true }
                        , { field: 'clientId', title: 'clientID', width: 120, edit: true }
                        , { field: 'cientSecret', title: 'oauth认证', width: 120, edit: true }
                        , { field: 'grantType', title: 'oauth类型', width: 160, edit: true }
                        , { field: 'scene', title: '环境', width: 100, edit: true }
                        , { field: 'updateTime', title: '更新时间', sort: true, width: 180, templet: "<div>{{layui.util.toDateString(d.updateTime,'yyyy-MM-dd HH:mm:ss')}}</div>" }
                        , { fixed: 'right', title: "操作", align: "center", toolbar: '#barDemo', width: 260 }
                    ]
                ]
                , id: 'testReload'
                , page: true
//                , height: 600
                , even: true ///开启隔行背景
            });

            // 工具监听
            table.on('tool(table_auth)', function (data) {
                switch (data.event) {
                    case 'del':
                        var tableObj = tableOauth;
                        var config = tableObj.config;
                        var dataTemp = config.data;
                        var page = config.page;
                        // 得到tr的data-index
                        var trElem = data.tr.first();
                        var index = trElem.data('index');
                        // 计算出在data中的index
                        var dataIndex = index + page.limit * (page.curr - 1);
                        console.log('===' + dataIndex);
                        // 删除对应下标的数据
                        dataTemp.splice(dataIndex, 1);

                        // 新的页数
                        var pageNew = Math.ceil(dataTemp.length / page.limit);
                        // 重新接收reload返回的对象，这个很重要
                        tableOauth = table.reload(config.id, $.extend(true, {
                        // 更新数据
                            data: dataTemp
                        }, config.page ? {
                        // 如果删除了数据之后页数变了，打开前一页
                            page: {
                                curr: page.curr > pageNew ? ((page.curr - 1) || 1) : page.curr
                            }
                        } : {}));
                            break;
                            default:
                            break;
                            }
                        });

            var rownumOauth = 1;
                        // 新增一条记录
            $('#createNewOauth').click(function () {
                var lasnum = rownumOauth - 1;
                rownumOauth += 1;
                var tableObj = tableOauth;
                var config = tableObj.config;
                var dataTemp = config.data;
                var lastdata = [{'id': 1}]
                lastdata.id = rownumOauth;
                dataTemp.push(lastdata);
                tableOauth = table.reload(config.id, $.extend(true, {
                 // 更新数据
                     data: dataTemp,
                 }, config.page ? {
                 // 一般新增都是加到最后，所以始终打开最后一页
                 page: {
                    curr: Math.ceil(dataTemp.length / config.page.limit)
                 }
                 } : {}));
                    return false;
                 });

             //监听工具条
             table.on('tool(table_auth)', function(obj){
                var data = obj.data;
                if(obj.event === 'detail'){
                    layer.msg('ID：'+ data.id + ' 的查看操作');
                } else if(obj.event === 'del'){
                    layer.confirm('真的删除行么', function(index){
                    deleteOauth(data);
                });
                } else if(obj.event === 'edit'){
                    openWin(data,'修改信息','/muda/platform/plan/platformPlan_edit.html',650,600);
                }
              });


            //删除方法
            function deleteOauth(data){
                $.ajax({
                    url: beforeURL+'/delete',
                    type: 'post',
                    dataType: 'text',
                    //contentType: 'application/json;charset=utf-8',
                    //此处如果是json格式，只传ID的话后台收不到参数
                    contentType: 'application/x-www-form-urlencoded',
                    data: {'id':data.id},
                    error : function(request) {
			            parent.layer.alert("删除失败");
		            },
                    success: function (data1) {
                        parent.layer.alert("删除成功",{icon: 6});
                        location.reload(true);
                    }
                })
             };

            var objTable = '[]';
            //监听表格复选框选择
            table.on('radio(table_auth)', function (obj) {
                var checkStatus = table.checkStatus('testReload');
                objTable = checkStatus.data;
            });
});