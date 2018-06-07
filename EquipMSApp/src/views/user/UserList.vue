<!--   用户列表    -->
<template>
<div class="userlist-wrapper">
	<mini-bar title="用户列表" back="true"></mini-bar>
	<div class="main">
		<list class="list">
			<refresh class="refresh" @refresh="onrefresh"  :display="refreshing ? 'show' : 'hide'">
      	<text class="indicator-text">刷新中 ...</text>
      	<loading-indicator class="indicator"></loading-indicator>
    	</refresh>
			<header class="title">
				<text class="title-cell" style="flex:1">姓名</text>
				<text class="title-cell" style="flex:2">部门</text>
			</header>
			<cell v-for="(user,i) in userList" :key="i">
				<div class="content" @click="userDetail(user.id)">
					<div class="content-cell" style="flex:1">
						<text class="content-text">{{user.truename}}</text>
					</div>
					<div class="content-cell" style="flex:2">
						<text class="content-text">{{user.deptname}}</text>
					</div>
					
					
				</div>
			</cell>
			<cell style="align-items:center" v-if="!haveMore"><text class="more">没有更多了</text></cell>
			<loading class="loading" @loading="onloading" :display="loading ? 'show' : 'hide'" v-if="haveMore">
      	<text class="indicator-text">加载中 ...</text>
      	<loading-indicator class="indicator"></loading-indicator>
    	</loading>
		</list>
		<button @click="showPopup()" class="condition" style="justify-content:center"><text style="color: #00bfff;text-align:center">按条件查询</text></button>
		<wxc-popup :show="isShow" @wxcPopupOverlayClicked="popupOverlayBottomClick"
               pos="bottom" height="400">
			<div class="row" style="margin-top:40px">
				<text class="label">部门：</text>
				<button @click="showSelectList()" class="input" style="justify-content:center"><text>{{data.deptName}}</text></button>
				<text class="iconfont label" @click="initDept">&#xe69a;</text>
			</div>
			<div class="row">
				<text class="label">姓名：</text>
				<input class="input" type="text" v-model="data.name">
				<text class="iconfont label" @click="searchByName">&#xe6ac;</text>
			</div>
    </wxc-popup>
		<wxc-loading :show="isLoadingShow" type="trip"></wxc-loading>
		<list-select :list="deptList" title="选择部门" valStr="deptname" ref="deptlist" @listSelected="listSelected"></list-select>
	</div>
</div>
</template>
<script>
import { MiniBar, ListSelect } from '@/views/components'
import { WxcPopup, WxcLoading } from 'weex-ui'
import config from '@/config'
export default {
	data: () => ({
		data: {
			pageSize: config.pageSize,
			pageNum: 1,
			dept: '',
			deptName: '',
			name: ''
		},
		userList: [],
		haveMore: false,
		loading: false,
		refreshing: false,
		deptList: {},
		isShow: false,
		isLoadingShow: false
	}),
	methods: {
		onrefresh(event) {
			this.refreshing = true
			this.getUserList()
		},
		onloading(event) {
			this.loading = true
			this.data.pageNum++
			this.get('/getUserList', this.data, (result) => {
				if (result && result.state == 'success') {
					for (let obj of result.data.list) {
						this.userList.push(obj)
					}
					this.loading = false
					this.ifHaveMore(result.data.totalPage)
				}
			})
		},
		getUserList() {
			this.data.pageNum = 1
			this.get('/getUserList', this.data, (result) => {
				if (result && result.state == 'success') {
					this.refreshing = false
					this.userList = result.data.list
					this.ifHaveMore(result.data.totalPage)
					this.isLoadingShow = false
				} else {
					this.isLoadingShow = false
					this.refreshing = false
				}
			})
		},
		userDetail(id) {
			this.jumpWithParams('UserDetail', { id: id })
		},
		searchByName() {
			this.getUserList()
		},
		initDept() {
			this.data.dept = ''
			this.data.pageNum = 1
			this.data.deptName = ''
			this.getUserList()
		},
		ifHaveMore(totalPage) {
			if (this.data.pageNum >= totalPage) {
				this.haveMore = false
			} else {
				this.haveMore = true
			}
		},
		showSelectList() {
			this.isShow = false
			this.$refs['deptlist'].show()
		},
		showPopup() {
			this.isShow = true
		},
		listSelected(e) {
			this.isShow = true
			this.data.dept = e.obj.id
			this.data.deptName = e.obj.deptname
			this.data.pageNum = 1
			this.getUserList()
		},
		popupOverlayBottomClick() {
			this.isShow = false
		}
	},
	created() {
		this.isLoadingShow = true
		this.getUserList()
		this.getData(this.KEY_DEPTLIST, (res) => {
			if (res) {
				this.deptList = JSON.parse(res)
			}
		})
	},
	components: {
		MiniBar,
		ListSelect,
		WxcPopup,
		WxcLoading
	}
}
</script>
<style scoped>
.iconfont {
  font-family: iconfont;
}
.userlist-wrapper {
  background-color: #f0f0f0;
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 0;
}
.main {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 120px;
}
.list {
  position: absolute;
  width: 720px;
  margin-left: 15px;
  top: 0;
  bottom: 140px;
}
.title {
  flex-direction: row;
  width: 720px;
}
.title-cell {
  background-color: turquoise;
  text-align: center;
  height: 80px;
  line-height: 80px;
  border-width: 1px;
  border-color: rgba(128, 128, 128, 0.4);
}
.content {
  flex-direction: row;
}
.content-cell {
  height: 90px;
  justify-content: center;
  border-width: 1px;
  border-color: rgba(128, 128, 128, 0.4);
}
.content-text {
  text-align: center;
  word-break: break-all;
  font-size: 32px;
}
.loading,
.refresh {
  width: 750;
  display: -ms-flex;
  display: -webkit-flex;
  display: flex;
  -ms-flex-align: center;
  -webkit-align-items: center;
  -webkit-box-align: center;
  align-items: center;
}
.indicator-text,
.more {
  color: #888888;
  margin-top: 10px;
  text-align: center;
}
.indicator {
  margin-top: 16px;
  height: 40px;
  width: 40px;
  color: blue;
}
.condition {
  position: absolute;
  bottom: 8px;
  height: 80px;
  width: 350px;
  align-items: center;
  margin-left: 200px;
}
.row {
  position: relative;
  flex-direction: row;
  padding-left: 60px;
}
.label {
  height: 80px;
  width: 120px;
  line-height: 80px;
  text-align: center;
}
.input {
  height: 80px;
  width: 360px;
  padding-left: 20px;
  padding-right: 20px;
  border-bottom-color: #00bfff;
  border-bottom-width: 1px;
  margin-right: 20px;
}
</style>
