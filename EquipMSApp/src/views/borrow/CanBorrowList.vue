<template>
<div class="canborrowlist-wrapper">
	<mini-bar title="可借用设备列表" back="true"></mini-bar>
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
				<text class="title-cell">所属部门</text>
			</header>
			<cell v-for="(equip,i) in equipList" :key="i">
				<div class="content" @click="option(i)" ref="cell">
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
            <text class="content-text">{{equip.deptName}}</text>
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
				<button @click="showSelectList()" class="input" style="justify-content:center"><text>{{data.typename}}</text></button>
				<text class="iconfont label" @click="initType">&#xe69a;</text>
			</div>
			<div class="row">
				<text class="label">设备名称：</text>
				<input class="input" type="text" v-model="data.name">
				<text class="iconfont label" @click="searchByName">&#xe6ac;</text>
			</div>
    </wxc-popup>
    <wxc-popover ref="wxc-popover"
     :buttons="btns"
                 :position="popoverPosition"
                 :arrowPosition="popoverArrowPosition"
                 @wxcPopoverButtonClicked="popoverButtonClicked"></wxc-popover>
	</div>
  <list-select :list="typeList" title="选择设备种类" valStr="name" ref="typelist" @listSelected="listSelected"></list-select>
  <wxc-loading :show="isLoadingShow" type="trip"></wxc-loading>
  <alert ref="alert"></alert>
  <alert ref="confirmAlert" @dialogConfirmBtnClick="confirm" :single="false"></alert>
</div>
</template>
<script>
import { MiniBar, ListSelect, Alert } from '@/views/components'
import { WxcLoading, WxcPopup, WxcPopover } from 'weex-ui'
import config from '@/config'
export default {
  data: () => ({
    data: {
      pageSize: config.pageSize,
      pageNum: 1,
      name: '',
      type: '',
      dept: '',
      userid: '',
      typename: '',
    },
    btns: [{ text: '借用', key: 'borrow' }],
    popy: 400,
    popoverArrowPosition: { pos: 'left', x: 0 },
    equipList: {},
    haveMore: false,
    loading: false,
    refreshing: false,
    typeList: {},
    isLoadingShow: true,
    isPopupShow: false,
    i: 0
  }),
  methods: {
    option(i) {
      this.getModule('dom').getComponentRect(this.$refs.cell[i], (res) => {
        if (res.result) {
          this.popy = res.size.top
        }
      })
      this.i = i
      this.$refs['wxc-popover'].wxcPopoverShow();
    },
    popoverButtonClicked(obj) {
      this.$refs.confirmAlert.show("确定借用此设备吗？")

    },
    confirm() {
      let data = {
        equipId: this.equipList[this.i].id,
        userId: this.data.userid
      }

      this.post('/borrowEquip', data, (result) => {
        if (result && result.state == "success") {
          this.$refs.alert.show("借用成功！请等待审核")
          this.getCanBorrowList()
        } else if (result && result.state == "fail") {
          this.$refs.alert.show(result.data)
        } else {
          this.$refs.alert.show("借用失败")
        }
      })
    },
    listSelected(e) {
      this.isPopupShow = true
      this.data.type = e.obj.id
      this.data.typename = e.obj.name
      this.getCanBorrowList()
    },
    showSelectList() {
      this.isPopupShow = false
      this.$refs['typelist'].show()
    },
    onrefresh(event) {
      this.refreshing = true
      this.data.pageNum = 1
      this.getCanBorrowList()
    },
    onloading(event) {
      this.loading = true
      this.data.pageNum++
      this.get('/getCanBorrowList', this.data, (result) => {
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
      this.getCanBorrowList()
    },
    getCanBorrowList() {
      this.data.pageNum = 1
      this.get('/getCanBorrowList', this.data, (result) => {
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
      this.getCanBorrowList()
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
  computed: {
    popoverPosition() {
      return { x: -14, y: this.popy }
    }
  },
  created() {
    this.isLodingShow = true
    this.getData(this.KEY_EQUIPTYPE, (res) => {
      if (res) {
        this.typeList = JSON.parse(res)
      }
    })
    this.getData(this.KEY_USER, (res) => {
      if (res) {
        this.data.dept = JSON.parse(res).dept
        this.data.userid = JSON.parse(res).id
      }
    })
    setTimeout(() => {
      this.getCanBorrowList()
    }, 200)

  },
  components: {
    MiniBar,
    ListSelect,
    WxcLoading,
    WxcPopup,
    WxcPopover,
    Alert
  }
}
</script>
<style scoped>
.iconfont {
  font-family: iconfont;
}
.canborrowlist-wrapper {
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
