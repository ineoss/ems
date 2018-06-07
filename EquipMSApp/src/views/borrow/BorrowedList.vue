<!--   待归还设备列表    -->
<template>
<div class="borrowedlist-wrapper">
	<mini-bar title="待归还设备列表" back="true"></mini-bar>
	<div class="main">
		<list class="list">
			<refresh class="refresh" @refresh="onrefresh"  :display="refreshing ? 'show' : 'hide'">
      	<text class="indicator-text">刷新中 ...</text>
      	<loading-indicator class="indicator"></loading-indicator>
    	</refresh>
			<header class="title">
        <text class="title-cell">资产编号</text>
				<text class="title-cell">设备名称</text>
        <text class="title-cell">设备种类</text>
        <text class="title-cell">借出部门</text>
        <text class="title-cell">租借部门</text>
			</header>
			<cell v-for="(equip,i) in equipList" :key="i">
				<div class="content" @click="detail(equip.id)">
          <div class="content-cell">
            <text class="content-text">{{equip.assetNo}}</text>
          </div>
          <div class="content-cell">
            <text class="content-text">{{equip.equipName}}</text>
          </div>
          <div class="content-cell">
					  <text class="content-text">{{equip.typeName}}</text>
          </div>
          <div class="content-cell">
            <text class="content-text">{{equip.outDept}}</text>
          </div>
          <div class="content-cell">
            <text class="content-text">{{equip.inDept}}</text>
          </div>
				</div>
			</cell>
			<cell style="align-items:center" v-if="!haveMore"><text class="more">没有更多了</text></cell>
			<loading class="loading" @loading="onloading" :display="loading ? 'show' : 'hide'" v-if="haveMore">
      	<text class="indicator-text">加载中 ...</text>
      	<loading-indicator class="indicator"></loading-indicator>
    	</loading>
		</list>
    <div class="condition">
			<div class="row">
				<text class="label">设备名称：</text>
				<input class="input" type="text" v-model="data.equipName">
				<text class="iconfont label" @click="searchByName">&#xe6ac;</text>
			</div>
		</div>
	</div>
  <wxc-loading :show="isLoadingShow" type="trip"></wxc-loading>
  <alert ref="alert"></alert>
</div>
</template>
<script>
import { MiniBar, Alert } from '@/views/components'
import { WxcLoading } from 'weex-ui'
import config from '@/config'
export default {
  data: () => ({
    data: {
      pageSize: config.pageSize,
      pageNum: 1,
      equipName: ''
    },
    equipList: {},
    haveMore: false,
    loading: false,
    refreshing: false,
    isLoadingShow: true,
  }),
  methods: {
    detail(id) {
      this.jumpWithParams('BorrowedDetail', { id: id })
    },
    onrefresh(event) {
      this.refreshing = true
      this.data.pageNum = 1
      this.getBorrowedList()
    },
    searchByName() {
      this.getBorrowedList()
    },
    onloading(event) {
      this.loading = true
      this.data.pageNum++
      this.get('/getBorrowedList', this.data, (result) => {
        if (result && result.state == 'success') {
          for (let obj of result.data.list) {
            this.equipList.push(obj)
          }
          this.loading = false
          this.ifHaveMore(result.data.totalPage)
        }
      })
    },
    getBorrowedList() {
      this.data.pageNum = 1
      this.get('/getBorrowedList', this.data, (result) => {
        if (result && result.state == 'success') {
          this.refreshing = false
          this.equipList = result.data.list
          this.ifHaveMore(result.data.totalPage)
          this.isLoadingShow = false
        } else {
          this.isLoadingShow = false
        }
      })
    },
    ifHaveMore(totalPage) {
      if (this.data.pageNum >= totalPage) {
        this.haveMore = false
      } else {
        this.haveMore = true
      }
    },
  },
  created() {
    this.isLodingShow = true
    setTimeout(() => {
      this.getBorrowedList()
    }, 200)

  },
  components: {
    MiniBar,
    WxcLoading,
    Alert
  }
}
</script>
<style scoped>
.iconfont {
  font-family: iconfont;
}
.borrowedlist-wrapper {
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
  top: 100px;
}
.list {
  position: absolute;
  width: 720px;
  margin-left: 15px;
  top: 20px;
  bottom: 140px;
}
.title {
  flex-direction: row;
  width: 720px;
}
.title-cell {
  flex: 1;
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
  flex: 1;
  height: 120px;
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

.row {
  position: relative;
  flex-direction: row;
  padding-left: 60px;
}
.label {
  height: 80px;
  width: 180px;
  line-height: 80px;
  text-align: left;
}
.input {
  height: 80px;
  width: 320px;
  padding-left: 20px;
  padding-right: 20px;
  border-bottom-color: #00bfff;
  border-bottom-width: 1px;
  padding-bottom: 0;
  margin-right: 20px;
}
.condition {
  position: absolute;
  bottom: 8px;
  height: 120px;
}
</style>
