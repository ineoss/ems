<!--   近期需检修设备列表    -->
<template>
<div class="stock-wrapper">
	<mini-bar title="近期需检修设备" back="true"></mini-bar>
	<div class="main">
		<list class="list">
			<refresh class="refresh" @refresh="onrefresh"  :display="refreshing ? 'show' : 'hide'">
      	<text class="indicator-text">刷新中 ...</text>
      	<loading-indicator class="indicator"></loading-indicator>
    	</refresh>
			<header class="title">
        <text class="title-cell">资产编号</text>
				<text class="title-cell">设备名称</text>
        <text class="title-cell">存放位置</text>
        <text class="title-cell">上次检修日</text>
			</header>
			<cell v-for="(item,i) in equipList" :key="i">
				<div class="content">
          <div class="content-cell">
            <text class="content-text">{{item.assetNo}}</text>
          </div>
          <div class="content-cell">
            <text class="content-text">{{item.equipName}}</text>
          </div>
          <div class="content-cell">
            <text class="content-text">{{item.pos}}</text>
          </div>
          <div class="content-cell">
            <text class="content-text">{{item.lastRepaire}}</text>
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
				<input class="input" type="text" v-model="data.name">
				<text class="iconfont label" @click="searchByName">&#xe6ac;</text>
			</div>
		</div>
	</div>
  <wxc-loading :show="isLoadingShow" type="trip"></wxc-loading>
</div>
</template>
<script>
import { MiniBar } from '@/views/components'
import { WxcLoading } from 'weex-ui'
import config from '@/config'
export default {
  data: () => ({
    data: {
      pageSize: config.pageSize,
      pageNum: 1,
      name: '',
    },
    equipList: {},
    haveMore: false,
    loading: false,
    refreshing: false,
    isLoadingShow: false,
  }),
  methods: {
    onrefresh(event) {
      this.refreshing = true
      this.data.pageNum = 1
      this.getNeedHaul()
    },
    onloading(event) {
      this.loading = true
      this.data.pageNum++
      this.get('/getNeedHaul', this.data, (result) => {
        if (result && result.state == 'success') {
          for (let obj of result.data.list) {
            this.equipList.push(obj)
          }
          this.loading = false
          this.refreshing = false
          this.ifHaveMore(result.data.totalPage)
        } else {
          this.loading = false
          this.refreshing = false
        }
      })
    },
    getNeedHaul() {
      this.data.pageNum = 1
      this.get('/getNeedHaul', this.data, (result) => {
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
    searchByName() {
      this.getNeedHaul()
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
    this.isLoadingShow = true
    setTimeout(() => {
      this.getNeedHaul()
    }, 200)

  },
  components: {
    MiniBar,
    WxcLoading,
  }
}
</script>
<style scoped>
.iconfont {
  font-family: iconfont;
}
.stock-wrapper {
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
  margin-top: 10px;
  margin-left: 15px;
  top: 0;
  bottom: 100px;
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
.condition {
  position: absolute;
  bottom: 0;
  height: 100px;
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
.popup-title {
  margin-top: 20px;
  margin-bottom: 20px;
  width: 750px;
  text-align: center;
}
.button {
  height: 70px;
  width: 320px;
  border-radius: 4px;
  background-color: orangered;
  text-align: center;
  line-height: 70px;
  margin-top: 30px;
  color: #ffffff;
}
</style>
