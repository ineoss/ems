<!--   库存列表    -->
<template>
<div class="stock-wrapper">
	<mini-bar title="库存列表" back="true"></mini-bar>
	<div class="main">
		<list class="list">
			<refresh class="refresh" @refresh="onrefresh"  :display="refreshing ? 'show' : 'hide'">
      	<text class="indicator-text">刷新中 ...</text>
      	<loading-indicator class="indicator"></loading-indicator>
    	</refresh>
			<header class="title" ref="cell">
				<text class="title-cell" >设备名称</text>
				<text class="title-cell" >库存数量</text>
        <text class="title-cell" >库存上限</text>
        <text class="title-cell" >库存下限</text>
			</header>
			<cell v-for="(item,i) in stockList" :key="i">
				<div class="content" @click="option(i)" ref="cell" >
          <div class="content-cell">
					  <text class="content-text">{{item.equipName}}</text>
          </div>
          <div class="content-cell">
					  <text class="content-text">{{item.num}}</text>
          </div>
          <div class="content-cell">
            <text class="content-text">{{item.max}}</text>
          </div>
          <div class="content-cell">
            <text class="content-text">{{item.min}}</text>
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
    <wxc-popup :show="isPopupShow" @wxcPopupOverlayClicked="popupOverlayClick"
               pos="bottom" height="400">
      <text class="popup-title">{{stock.equipName}}</text>
      <div class="row">
				<text class="label">库存上限：</text>
				<input class="input" type="number" v-model="stock.max">
			</div>
      <div class="row">
				<text class="label">库存下限：</text>
				<input class="input" type="number" v-model="stock.min">
			</div>
      <div class="row">
				<text class="label"></text>
				<text @click="submit" class="button">确定</text>
			</div>
    </wxc-popup>
     <wxc-popover ref="wxc-popover"
     :buttons="btns"
                 :position="popoverPosition"
                 :arrowPosition="popoverArrowPosition"
                 @wxcPopoverButtonClicked="popoverButtonClicked"></wxc-popover>
      <wxc-loading :show="isLoadingShow" type="trip"></wxc-loading>
      <alert ref="alert"></alert>
	</div>
</div>
</template>
<script>
import { MiniBar, Alert } from '@/views/components'
import { WxcPopover, WxcLoading, WxcPopup } from 'weex-ui'
import config from '@/config'
export default {
  data: () => ({
    data: {
      pageSize: config.pageSize,
      pageNum: 1,
      name: '',
      state: ''
    },
    stockList: {},
    stock: {},
    haveMore: false,
    loading: false,
    refreshing: false,
    btns: [{ text: '详细信息', key: 'detail' }, { text: '设置预警数量', key: 'setNum' }],
    name: '',
    popy: 400,
    popoverArrowPosition: { pos: 'left', x: 0 },
    isLoadingShow: false,
    isPopupShow: false
  }),
  methods: {
    option(i) {
      this.getModule('dom').getComponentRect(this.$refs.cell[i], (res) => {
        if (res.result) {
          this.popy = res.size.top
        }
      })
      this.stock.id = this.stockList[i].id
      this.stock.equipName = this.stockList[i].equipName
      this.stock.num = this.stockList[i].num
      this.stock.max = this.stockList[i].max == null ? '' : this.stockList[i].max
      this.stock.min = this.stockList[i].min == null ? '' : this.stockList[i].min
      this.name = this.stockList[i].equipName
      this.$refs['wxc-popover'].wxcPopoverShow();
    },
    submit() {
      if (parseInt(this.stock.max) < parseInt(this.stock.min)) {
        this.$refs.alert.show("【库存上限】不能小于【库存下限】")
        return
      }
      this.post("/modifyStock", this.stock, (result) => {
        if (result && result.state == 'success') {
          this.isPopupShow = false
          this.onrefresh()
        } else {
          this.$refs.alert.show("设置失败，请重试")
        }
      })
    },
    popoverButtonClicked(obj) {
      if (obj.key == 'detail') {
        this.jumpWithParams('EquipList', { name: this.name, state: '在库' })
      } else {
        this.isPopupShow = true
      }
    },
    onrefresh(event) {
      this.refreshing = true
      this.data.pageNum = 1
      this.getStockList()
    },
    onloading(event) {
      this.loading = true
      this.data.pageNum++
      this.get('/getStockList', this.data, (result) => {
        if (result && result.state == 'success') {
          for (let obj of result.data.list) {
            this.stockList.push(obj)
          }
          this.loading = false
          this.ifHaveMore(result.data.totalPage)
        }
      })
    },
    getStockList() {
      this.data.pageNum = 1
      this.get('/getStockList', this.data, (result) => {
        if (result && result.state == 'success') {
          this.refreshing = false
          this.stockList = result.data.list
          this.ifHaveMore(result.data.totalPage)
          this.isLoadingShow = false
        } else {
          this.isLoadingShow = false
        }
      })
    },
    searchByName() {
      this.getStockList()
    },
    ifHaveMore(totalPage) {
      if (this.data.pageNum >= totalPage) {
        this.haveMore = false
      } else {
        this.haveMore = true
      }
    },
    popupOverlayClick() {
      this.isPopupShow = false
    }
  },
  computed: {
    popoverPosition() {
      return { x: -14, y: this.popy }
    }
  },
  created() {
    this.isLoadingShow = true
    this.getData(this.KEY_EQUIPSTATE, (res) => {
      if (res) {
        let t = JSON.parse(res)
        for (let i of t) {
          if (i.name == '在库') {
            this.data.state = i.id
            break;
          }
        }
      }
    })
    this.getStockList()
  },
  components: {
    MiniBar,
    WxcPopover,
    WxcLoading,
    WxcPopup,
    Alert
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
  margin-left: 15px;
  top: 10px;
  bottom: 120px;
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
  height: 120px;
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
