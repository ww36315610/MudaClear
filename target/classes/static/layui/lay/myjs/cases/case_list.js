layui.use(['table','form','element'], function(){
  var table = layui.table;
  var form = layui.form;
  var element = layui.element;
  var beforeURL = "/muda/case";

  element.render();

  //方法级渲染
  csvo = table.render({
    elem: '#LAY_table_user'
    ,url: beforeURL+'/get'
    ,toolbar: '#toolbarDemo'
    ,title: '用户数据表'
    ,request:{
        pageName:'pageNum' // 页码的参数名称，默认：page
        ,limitName:'pageSize' //每页数据量的参数名，默认：limit
    }
    ,response: {
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: 200, //成功的状态码，默认：0
        msgName: 'msg', //状态信息的字段名称，默认：msgz
        countName: 'count', //数据总数的字段名称，默认：count
        dataName: 'data' //数据列表的字段名称，默认：data
    }
    ,cols: [
     [
      {checkbox: true, fixed: true}
      ,{field:'id', title: 'ID', width:80, sort: true, fixed: true}
      ,{field:'caseName', title: 'case名称',sort: true, width:120, fixed: true}
      ,{field:'caseCode', title: 'caseID', sort: true, width:120, fixed: true}
      ,{field:'projectId', title: '所属项目', sort: true, width:120, unresize: true}
      ,{field:'sortNO', title: 'case顺序',sort: true, width:100}
      ,{field:'status', title:'状态', width:80, templet: '#switchDemo', unresize: true}
      ,{field:'message', title: 'case说明',width:500}
      ,{field:'updateTime', title: '更新时间', sort: true, width:150, templet:"<div>{{layui.util.toDateString(d.updateTime,'yyyy-MM-dd HH:mm:ss')}}</div>"}
      ,{field:'author', title: '操作者',width:100}
      ,{field: 'process', title: '进度', width: 150 ,
               templet:function(d){
                        var html = '<div class="layui-progress layui-progress-big" lay-showpercent="true" lay-filter='+d.id+'>';
                            html += '<div class="layui-progress-bar layui-bg-red" lay-percent="0%"></div>';
                            html += '</div>';
                        return html;
               }
       }
      ,{fixed:'right',title:"操作",align:"center",toolbar:'#barDemo',width:180}
     ]
    ]
    ,id: 'testReload'
    ,page: true
    ,height: 600
    ,even: true //开启隔行背景
    ,done:function(res,curr,count){
               element.render();
         }
  });


//头工具栏事件
  table.on('toolbar(demo)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
      case 'getCheckData':
        var data = checkStatus.data;
        layer.alert(JSON.stringify(data));
      break;
      case 'getCheckLength':
        var data = checkStatus.data;
        layer.msg('选中了：'+ data.length + ' 个');
      break;
      case 'isAll':
        layer.msg(checkStatus.isAll ? '全选': '未全选');
      break;
    };
  });

  //监听表格复选框选择
  table.on('checkbox(demo)', function(obj){
    console.log(obj)
  });
  //监听工具条
  table.on('tool(demo)', function(obj){
    var data = obj.data;
    if(obj.event === 'detail'){
        var othis = $(this);
        layer.msg('ID：'+ data.id + ' 的查看操作');
        findCount(data);
//        proBarPut(data);
//        proBarPutOne(data);
    } else if(obj.event === 'del'){
        layer.confirm('真的删除行么', function(index){
//      obj.del();
//      layer.close(index);
        deleteProject(data);
      });
    } else if(obj.event === 'edit'){
//      layer.alert('编辑行：<br>'+ JSON.stringify(data))
        openWin(data,'修改信息','/muda/case/case_edit.html',650,700);
    }
  });


  var $ = layui.$, active = {
   reload: function(){
      var demoReload = $('#demoReload');
      console.log("-------"+demoReload.val());
      //执行重载
      table.reload('testReload', {
        pageNum: {
          curr: 1 //重新从第 1 页开始
        }
        ,where: {
            caseName: demoReload.val()
        }
      }, 'data');
    }
  };


//监听switch操作
  form.on('switch(switchDemo)', function(obj){
    var params = {id:obj.value,status:obj.elem.checked?1:0};
    console.log(params);
        $.ajax({
            url: beforeURL+'/update',
            type : 'post',
            dataType: 'text',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(params),
            success : function (data1) {
                alert('更新成功');
            }
          });
    layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
    return false;
  });


  //删除方法
  function deleteProject(data){
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
//               alert('删除成功1');
               parent.layer.alert("删除成功",{icon: 6});
               location.reload(true);
            }
        })
  };

// 执行后台任务获取进度，更新进度条//findCount接口，获取进度执行，需要case执行的插入
  function findCount(data){
     var othis = $(this);
     var DISABLED = 'layui-btn-disabled';
     if (othis.hasClass(DISABLED)) return;
     //调用轮训函数
     var n = 0, timer = setInterval(function () {
        $.ajax({
            url: beforeURL+'/findCount',
            type : 'post',
            success : function (data1) {
                var othis = $(this);
                n = data1;
                if (n > 100) {
                    n = 100;
                    //暂停函数
                    clearInterval(timer);
                    othis.removeClass(DISABLED);
                }
                element.progress(data.id, n + '%');
             }
          })
      }, 300 + Math.random() * 1000);
      othis.addClass(DISABLED);
  }


  //编辑数据，打开自窗口方法
  function openWin(editData,title,url,w,h,full) {
    		if (title == null || title == '') {
    	        var title=false;
    	    };
    	    if (url == null || url == '') {
    	        var url="404.html";
    	    };
    	    if (w == null || w == '') {
    	        var w=($(window).width()*0.9);
    	    };
    	    if (h == null || h == '') {
    	        var h=($(window).height() - 50);
    	    };
    	    var index = layer.open({
    	        type: 2,
    	        area: [w+'px', h +'px'],
    	        fix: false, //不固定
    	        maxmin: true,
    	        shadeClose: true,
    	        shade:0.4,
    	        title: title,
    	        content: url,
    	        //打开窗口并传值过去
    	        success: function(){
    	            var body =  layer.getChildFrame('body',index);
                    body.find("input[name='id']").val(editData.id);
    	            body.find("input[name='caseName']").val(editData.caseName);
    	            body.find("input[name='caseCode']").val(editData.caseCode);
//    	            body.find("select[name='caseCode']").find("option[value="+editData.caseCode+"]").attr('selected', 'true');
                    var ss = editData.status;
                    if(ss==1){
                        body.find("input[name='status']").val(1);
                        body.find("input[name='status']").checked;
                        console.log('111--'+ss);
                    }else{
                        body.find("input[name='status']").val(0);
//                        body.find("input[name='status']").attr('checked', 'false');
                        console.log('222--'+ss);
                    }
                    body.find("input[name='sortNO']").val(editData.sortNO);
                    body.find("input[name='author']").val(editData.author);
                    body.find("input[name='updateTime']").val(layui.util.toDateString(editData.updateTime, "yyyy-MM-dd HH:mm:ss"));
                    body.find("textarea[name='message']").val(editData.message);
                    body.find("input[name='projectId']").val(editData.projectId);

    	        }
    	    });
    	    if(full){
    	       layer.full(index);
    	    }
    }


  $('.demoTable .layui-btn').on('click', function(){
    var othis = $(this);
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });

});

