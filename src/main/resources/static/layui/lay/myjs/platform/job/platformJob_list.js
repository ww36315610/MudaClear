layui.use(['jquery','table','form','element'], function(){
 $ = layer.jquery;

  var table = layui.table;
  var form = layui.form;
  var element = layui.element;
  var beforeURL = "/muda/patformJob";

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
      {checkbox: true, width:60,fixed: true}
      ,{field:'id', title: 'ID', width:60, align: "center", sort: true, fixed: true}
      ,{field:'jobName', title: 'job名称', width:150,align: "center", fixed: true}
      ,{field:'projectName', title: '归属项目', width:150,align: "center", fixed: true}
      ,{field:'status', title:'状态', width:100,align: "center", templet: '#switchDemo', unresize: true}
      ,{field:'crontab', title: '定时调度',width:120,align: "center",edit:true}
      ,{field:'beginTime', title: '开始时间', sort: true, width:150,align: "center", templet:"<div>{{layui.util.toDateString(d.beginTime,'yyyy-MM-dd HH:mm:ss')}}</div>"}
      ,{field: 'process', title: '进度', width: 200 ,align:'center',
            templet:function(d){
                var html = '<div class="layui-progress layui-progress-big" lay-showpercent="true" lay-filter='+d.id+'>';
                    html += '<div class="layui-progress-bar layui-bg-red" lay-percent="0%"></div>';
                    html += '</div>';
                return html;
            }
       }
       ,{field:'endTime', title: '结束时间', sort: true, width:150,align: "center", templet:"<div>{{layui.util.toDateString(d.endTime,'yyyy-MM-dd HH:mm:ss')}}</div>"}
       ,{field:'message', title: 'job说明',align: "center"}
       ,{field:'', title: '测试报告',align: "center", templet:"<div><a href='/muda/testNGReport.html'>testNGReport</a></div>"}
      ,{fixed:'right',title:"操作",align:"center",toolbar:'#barDemo',width:180}
     ]
    ]
    ,id: 'testReload'
    ,page: true
    ,height: 600
    ,even: true //开启隔行背景
    ,done:function(res,curr,count){
        element.render();//刷新进度条
//           改变table样式
         $('th').css({'background-color': '#5792c6', 'color': '#fff','font-weight':'bold'});
//         $('th').css({'background-color': '#1E9FFF', 'color': '#fff','font-weight':'bold'});
//           $('th').css({'background-color': '#009688', 'color': '#fff','font-weight':'bold'});
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
            //调用执行function
            start(data);
            //刷新进度条
            findCount(data);
      } else if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
            deleteProject(data);
            });
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
            planName: demoReload.val()
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


  //start_job方法
  function start(data){
//       var params = {id:obj.value,status:obj.elem.checked?1:0};
       var params = {jobName:'dd',projectName:'cc'};
        $.ajax({
            url: beforeURL+'/start',
            type: 'post',
            dataType: 'text', //这里是返回的结果类型【json,text】
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(params),//[入参格式]
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success: function (data1) {
                parent.layer.alert("执行成功",{icon: 6});
//                location.reload(true);
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
            url: '/muda/patformJob/findCount',
            type : 'post',
            success : function (data1) {
                var othis = $(this);
                n = data1;
                if (n >= 100) {
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
    	        console.log('----=-------=====----'+editData.planName);
    	            var body =  layer.getChildFrame('body',index);
                    body.find("input[name='id']").val(editData.id);
                    body.find("input[name='caseName']").val(editData.caseName);
    	            body.find("input[name='planName']").val(editData.planName);
    	            body.find("input[name='projectName']").val(editData.projectName);

                    var ss = editData.status;

                    if(ss==1){
                    console.log("11111======"+ss)
                        body.find("input[name='status']").val(1);
                        body.find("input[name='status']").attr("checked",true);
                    }else{
                    console.log("00000======"+ss)
                        body.find("input[name='status']").val(0);
                        body.find("input[name='status']").attr("checked",false);
                    }
                    body.find("input[name='author']").val(editData.author);
                    body.find("input[name='updateTime']").val(layui.util.toDateString(editData.updateTime, "yyyy-MM-dd HH:mm:ss"));
                    body.find("textarea[name='message']").val(editData.message);
    	         form.render("checkbox");
    	        }
    	    });
    	    if(full){
    	       layer.full(index);
    	    }
    }

  $('.demoTable .layui-btn').on('click', function(){
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });

});

