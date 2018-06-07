<!--    维修详情     -->
<template>
<div class="repairedetail-wrapper">
	<mini-bar title="维修详情" back="true"></mini-bar>
	<list class="main">
		<refresh class="refresh" @refresh="onrefresh"  :display="refreshing ? 'show' : 'hide'">
      		<text class="indicator-text">刷新中 ...</text>
      		<loading-indicator class="indicator"></loading-indicator>
    	</refresh>
		<cell class="cell">
		<image :src="image" class="image"/>
		<div class="row">
			<text class="label">设备名称：</text>
			<text class="input">{{equip.equipName}}</text>
		</div>
		<div class="row">
			<text class="label">资产编号：</text>
			<text class="input">{{equip.assetNo}}</text>
		</div>
		<div class="row">
			<text class="label">所属部门：</text>
			<text class="input">{{equip.deptName}}</text>
		</div>
		<div class="row">
			<text class="label">设备分类：</text>
			<text class="input">{{equip.typeName}}</text>
		</div>
		<div class="row">
			<text class="label">维修日期：</text>
			<text class="input">{{equip.repaireDate}}</text>
		</div>
		<div class="row">
			<text class="label">维修地点：</text>
			<text class="input">{{equip.pos}}</text>
		</div>
		<div class="row">
			<text class="label">维修说明：</text>
			<text class="input">{{equip.mark}}</text>
		</div>
		<div class="row">
			<text class="label">是否成功：</text>
			<text class="input">{{equip.success==1?'成功':'失败'}}</text>
		</div>
		<div class="row">
			<text class="label">维修员：</text>
			<text class="input">{{equip.handler}}</text>
		</div>
		<div class="button-group">
			<button><text @click="delEquipAlert" class="util" style="background-color:rgba(255,0,0,0.5)">删除</text></button>
		</div>
		</cell>
		<alert ref="delAlert" @dialogConfirmBtnClick="delEquip" :single="false"></alert>
		<alert ref="delSuccessAlert" @dialogConfirmBtnClick="delSuccess"></alert>
		<alert ref="alert"></alert>
	</list>
</div>
</template>
<script>
import { MiniBar, Alert } from '@/views/components'
import config from '@/config'
let server_ip = config.server_ip
export default {
	data() {
		return {
			equip: { image: '/images/default.jpg' },
			refreshing: false
		}
	},
	methods: {
		onrefresh(event) {
			this.refreshing = true
			this.get('/getRepaireInfo', { id: this.equip.id }, (result) => {
				if (result && result.state == 'success') {
					this.equip = result.data
				}
				this.refreshing = false
			})
		},
		delEquipAlert() {
			this.$refs['delAlert'].show('确定删除本条信息吗？')
		},
		delEquip() {
			this.post('/delRepaire', { id: this.equip.id }, (result) => {
				if (result && result.state == 'success') {
					this.$refs['delSuccessAlert'].show('删除成功')
				} else {
					this.$refs['alert'].show('删除失败')
				}
			})
		},
		delSuccess() {
			this.toBack()
		}
	},
	computed: {
		image() {
			return server_ip + this.equip.image
		}
	},
	created() {
		let params = this.getParams()
		if (params && params.id) {
			this.get('/getRepaireInfo', { id: params.id }, (result) => {
				if (result && result.state == 'success') {
					this.equip = result.data
				}
			})
		}
	},
	components: {
		MiniBar,
		Alert
	}
}
</script>
<style scoped>
.iconfont {
  font-family: iconfont;
}
.repairedetail-wrapper {
  background-color: #f0f0f0;
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 0;
}
.main {
  width: 750px;
  margin-top: 100px;
}
.cell {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  align-items: center;
}
.image {
  height: 320px;
  width: 560px;
  margin-bottom: 20px;
  margin-top: 20px;
  background-color: #ffffff;
}
.row {
  flex-direction: row;
  width: 750px;
  padding-left: 60px;
}
.label {
  height: 80px;
  width: 180px;
  text-align: center;
  line-height: 80px;
}
.input {
  height: 80px;
  width: 360px;
  border-radius: 4px;
  padding-left: 20px;
  padding-right: 20px;
  line-height: 80px;
}
.button-group {
  flex-direction: row;
  margin-top: 20px;
}
.util {
  height: 80px;
  text-align: center;
  line-height: 80px;
  border-radius: 8px;
  padding-left: 16px;
  padding-right: 16px;
  margin-left: 10px;
  margin-right: 10px;
  margin-bottom: 10px;
  width: 200px;
  color: white;
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
</style>
