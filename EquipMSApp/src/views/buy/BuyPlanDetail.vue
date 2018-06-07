<template>
<div class="buyplandetail-wrapper">
	<mini-bar title="购置计划详细信息" back="true"></mini-bar>
	<list class="main">
		<refresh class="refresh" @refresh="onrefresh"  :display="refreshing ? 'show' : 'hide'">
      		<text class="indicator-text">刷新中 ...</text>
      		<loading-indicator class="indicator"></loading-indicator>
    	</refresh>
		<cell class="cell">
		<div class="row" style="margin-top:40px">
			<text class="label">计划名称：</text>
			<text class="input">{{plan.name}}</text>
		</div>
		<div class="row">
			<text class="label">设备名称：</text>
			<text class="input">{{plan.equipName}}</text>
		</div>
		<div class="row">
			<text class="label">设备分类：</text>
			<text class="input">{{plan.typeName}}</text>
		</div>
		<div class="row">
			<text class="label">预购置部门：</text>
			<text class="input">{{plan.deptName}}</text>
		</div>
		<div class="row">
			<text class="label">预购置价格：</text>
			<text class="input">{{plan.plPrice}}元</text>
		</div>
		<div class="row">
			<text class="label">预购置数量：</text>
			<text class="input">{{plan.plNum}}</text>
		</div>
		<div class="row">
			<text class="label">预购置日期：</text>
			<text class="input">{{plan.plDate}}</text>
		</div>
		<div class="button-group">
			<button><text @click="delPlanAlert" class="util" style="background-color:rgba(255,0,0,0.5)">删除计划</text></button>
			<button><text @click="modifyPlan" class="util" style="background-color:rgb(0, 191, 255)">修改计划</text></button>
			<button><text @click="endPlan" class="util" style="background-color:rgb(217, 165, 32)">计划完成</text></button>
		</div>
		</cell>
		<alert ref="delAlert" @dialogConfirmBtnClick="delPlan" :single="false"></alert>
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
			plan: {},
			refreshing: false
		}
	},
	methods: {
		onrefresh(event) {
			this.refreshing = true
			this.get('/getBuyPlanInfo', { id: this.plan.id }, (result) => {
				if (result && result.state == 'success') {
					this.plan = result.data
				}
				this.refreshing = false
			})
		},
		delPlanAlert() {
			this.$refs['delAlert'].show('删除计划后不可恢复，确定要删除此计划吗？')
		},
		delPlan() {
			this.post('/delBuyPlan', { id: this.plan.id }, (result) => {
				if (result && result.state == 'success') {
					this.$refs['delSuccessAlert'].show('删除成功')
				} else {
					this.$refs['alert'].show('删除失败')
				}
			})
		},
		endPlan() {
			this.get('/isPlanEnd', { id: this.plan.id }, (result) => {
				if (result && result.state == "success") {
					if (result.data == "end") {
						this.$refs['alert'].show('该计划已完成')
					} else {
						this.jumpWithParams('EndBuyPlan', this.plan)
					}
				}
			})

		},
		delSuccess() {
			this.toBack()
		},
		modifyPlan() {
			this.jumpWithParams('ModifyBuyPlan', this.plan)
		}
	},
	created() {
		let params = this.getParams()
		if (params && params.id) {
			this.get('/getBuyPlanInfo', { id: params.id }, (result) => {
				if (result && result.state == 'success') {
					this.plan = result.data
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
.buyplandetail-wrapper {
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
.cell {
  position: absolute;
  left: 0;
  right: 0;
  top: 300px;
  align-items: center;
}
.row {
  flex-direction: row;
  width: 750px;
  padding-left: 60px;
  background-color: #ffffff;
}
.label {
  height: 80px;
  width: 200px;
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
  margin-top: 40px;
}
.util {
  height: 80px;
  width: 200px;
  text-align: center;
  line-height: 80px;
  border-radius: 8px;
  padding-left: 16px;
  padding-right: 16px;
  margin-left: 10px;
  margin-right: 10px;
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
