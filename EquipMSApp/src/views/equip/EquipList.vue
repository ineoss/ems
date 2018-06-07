<!--   设备列表    -->
<template>
<div class="equiplist-wrapper">
	<mini-bar title="设备列表" back="true"></mini-bar>
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
        <text class="title-cell">设备状态</text>
			</header>
			<cell v-for="(equip,i) in equipList" :key="i">
				<div class="content"  @click="equipDetail(equip.id)">
          <div class="content-cell">
            <text class="content-text">{{equip.assetNo}}</text>
          </div>
          <div class="content-cell">
            <text class="content-text">{{equip.name}}</text>
          </div>
          <div class="content-cell">
            <text class="content-text">{{equip.typeName}}</text>
          </div>
          <div class="content-cell">
            <text class="content-text">{{equip.stateName}}</text>
          </div>
				</div>
			</cell>
			<cell style="align-items:center" v-if="!haveMore"><text class="more">没有更多了</text></cell>
			<loading class="loading" @loading="onloading" :display="loading ? 'show' : 'hide'" v-if="haveMore">
      	<text class="indicator-text">加载中 ...</text>
      	<loading-indicator class="indicator"></loading-indicator>
    	</loading>
		</list>
		<button @click="showPopup" class="condition" style="justify-content:center"><text style="color: #00bfff;text-align:center">按条件查询</text></button>
    <wxc-popup :show="isPopupShow" @wxcPopupOverlayClicked="popupOverlayBottomClick"
               pos="bottom" height="400">
      <div class="row" style="margin-top:40px">
				<text class="label">设备分类：</text>
				<button @click="showSelectList('typelist')" class="input" style="justify-content:center"><text>{{data.typename}}</text></button>
				<text class="iconfont label" @click="initType">&#xe69a;</text>
			</div>
      <div class="row">
			  <text class="label">设备状态：</text>
			  <button @click="showSelectList('statelist')" class="input" style="justify-content:center"><text>{{data.statename}}</text></button>
        <text class="iconfont label" @click="initState">&#xe69a;</text>
      </div>
			<div class="row">
				<text class="label">设备名称：</text>
				<input class="input" type="text" v-model="data.name">
				<text class="iconfont label" @click="searchByName">&#xe6ac;</text>
			</div>
    </wxc-popup>
	</div>
  <list-select :list="typeList" title="选择设备种类" valStr="name" ref="typelist" @listSelected="listSelected"></list-select>
  <list-select :list="stateList" title="选择设备状态" valStr="name" ref="statelist" @listSelected="listSelected"></list-select>
  <wxc-loading :show="isLoadingShow" type="trip"></wxc-loading>
</div>
</template>
<script>
import { MiniBar, ListSelect } from '@/views/components'
import { WxcLoading, WxcPopup } from 'weex-ui'
import config from '@/config'
export default {
  data: () => ({
    data: {
      pageSize: config.pageSize,
      pageNum: 1,
      name: '',
      state: '',
      type: '',
      typename: '',
      statename: ''
    },
    equipList: {},
    haveMore: false,
    loading: false,
    refreshing: false,
    typeList: {},
    stateList: {},
    select_flag: '',
    isLoadingShow: true,
    isPopupShow: false
  }),
  methods: {
    listSelected(e) {
      this.isPopupShow = true
      if (this.select_flag == 'typelist') {
        this.data.type = e.obj.id
        this.data.typename = e.obj.name
      } else if (this.select_flag == 'statelist') {
        this.data.state = e.obj.id
        this.data.statename = e.obj.name
      }
      this.getEquipList()
    },
    showSelectList(key) {
      this.isPopupShow = false
      this.select_flag = key
      this.$refs[key].show()
    },
    onrefresh(event) {
      this.refreshing = true
      this.data.pageNum = 1
      this.getEquipList()
    },
    onloading(event) {
      this.loading = true
      this.data.pageNum++
      this.get('/getEquipList', this.data, (result) => {
        if (result && result.state == 'success') {
          for (let obj of result.data.list) {
            this.equipList.push(obj)
          }
          this.loading = false
          this.ifHaveMore(result.data.totalPage)
        }
      })
    },
    initType() {
      this.data.type = ''
      this.data.typename = ''
      this.getEquipList()
    },
    initState() {
      this.data.state = ''
      this.data.statename = ''
      this.getEquipList()
    },
    getEquipList() {
      this.data.pageNum = 1
      this.get('/getEquipList', this.data, (result) => {
        if (result && result.state == 'success') {
          this.refreshing = false
          this.equipList = result.data.list
          this.ifHaveMore(result.data.totalPage)
          this.isLoadingShow = false
        } else {
          this.isLoadingShow = false
          this.refreshing = false
        }
      })
    },
    searchByName() {
      this.getEquipList()
    },
    equipDetail(id) {
      this.jumpWithParams("EquipDetail", { id: id })
    },
    ifHaveMore(totalPage) {
      if (this.data.pageNum >= totalPage) {
        this.haveMore = false
      } else {
        this.haveMore = true
      }
    },
    showPopup() {
      this.isPopupShow = true
    },
    popupOverlayBottomClick() {
      this.isPopupShow = false
    }
  },
  created() {
    this.isShow = true
    let params = this.getParams()
    if (params && params.name) {
      this.data.name = params.name
    }
    this.getData(this.KEY_EQUIPTYPE, (res) => {
      if (res) {
        this.typeList = JSON.parse(res)
      }
    })
    this.getData(this.KEY_EQUIPSTATE, (res) => {
      if (res) {
        this.stateList = JSON.parse(res)
        if (params && params.state) {
          for (let i of this.stateList) {
            if (params.state == i.name) {
              this.data.state = i.id
              this.data.statename = i.name
              break
            }
          }
        }
      }
    })

    setTimeout(() => {
      this.getEquipList()
    }, 200)

  },
  components: {
    MiniBar,
    ListSelect,
    WxcLoading,
    WxcPopup
  }
}
</script>
<style scoped>
.iconfont {
  font-family: iconfont;
}
.equiplist-wrapper {
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
</style>
