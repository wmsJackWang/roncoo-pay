$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'accountinfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'ID', width: 50, key: true },
			{ label: '没有描述', name: 'accountAmt', index: 'ACCOUNT_AMT', width: 80 }, 			
			{ label: '没有描述', name: 'salt', index: 'SALT', width: 80 }, 			
			{ label: '没有描述', name: 'verityLevel', index: 'VERITY_LEVEL', width: 80 }, 			
			{ label: '没有描述', name: 'realName', index: 'REAL_NAME', width: 80 }, 			
			{ label: '没有描述', name: 'accountRole', index: 'ACCOUNT_ROLE', width: 80 }, 			
			{ label: '没有描述', name: 'credentialsNumber', index: 'CREDENTIALS_NUMBER', width: 80 }, 			
			{ label: '没有描述', name: 'updatedOn', index: 'UPDATED_ON', width: 80 }, 			
			{ label: '没有描述', name: 'userName', index: 'USER_NAME', width: 80 }, 			
			{ label: '没有描述', name: 'onwayAmt', index: 'ONWAY_AMT', width: 80 }, 			
			{ label: '没有描述', name: 'versionOptimizedLock', index: 'VERSION_OPTIMIZED_LOCK', width: 80 }, 			
			{ label: '没有描述', name: 'bindEmail', index: 'BIND_EMAIL', width: 80 }, 			
			{ label: '没有描述', name: 'accountStatus', index: 'ACCOUNT_STATUS', width: 80 }, 			
			{ label: '没有描述', name: 'bindMobile', index: 'BIND_MOBILE', width: 80 }, 			
			{ label: '没有描述', name: 'lastLoginTime', index: 'LAST_LOGIN_TIME', width: 80 }, 			
			{ label: '没有描述', name: 'accountType', index: 'ACCOUNT_TYPE', width: 80 }, 			
			{ label: '没有描述', name: 'totalSubsidyAmt', index: 'TOTAL_SUBSIDY_AMT', width: 80 }, 			
			{ label: '没有描述', name: 'freezeTime', index: 'FREEZE_TIME', width: 80 }, 			
			{ label: '没有描述', name: 'createdOn', index: 'CREATED_ON', width: 80 }, 			
			{ label: '没有描述', name: 'withdrawAmt', index: 'WITHDRAW_AMT', width: 80 }, 			
			{ label: '没有描述', name: 'merchantId', index: 'MERCHANT_ID', width: 80 }, 			
			{ label: '没有描述', name: 'password', index: 'PASSWORD', width: 80 }, 			
			{ label: '没有描述', name: 'lastIp', index: 'LAST_IP', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		accountInfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.accountInfo = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.accountInfo.id == null ? "accountinfo/save" : "accountinfo/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.accountInfo),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "accountinfo/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "accountinfo/info/"+id, function(r){
                vm.accountInfo = r.accountInfo;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});