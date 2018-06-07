<template>
<div class="endbuyplan-wrapper">
	<mini-bar title="完成购置计划" back="true"></mini-bar>
	<div class="main">
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
		<div class="row">
			<text class="label">购置价格：</text>
			<input class="input" type="number" v-model="plan.rPrice" autofocus="true">
    	<text class="required">*</text>
		</div>
		<div class="row">
			<text class="label">购置数量：</text>
			<input class="input" type="number" v-model="plan.rNum">
    	<text class="required">*</text>
		</div>
		<div class="row">
			<text class="label">购置日期：</text>
			<input class="input" type="date" v-model="plan.rDate">
    	<text class="required">*</text>
		</div>
		<button @click="submit" class="button"><text class="button-text">确认</text></button>
	</div>
	<alert ref="alert"></alert>
	<alert ref="successAlert" @dialogConfirmBtnClick="success"></alert>
</div>
</template>
<script>
import { MiniBar, Alert } from '@/views/components'
export default {
	data() {
		return {
			plan: {
				id: '',
				name: '',
				equipName: '',
				equipType: '',
				plPrice: '',
				plNum: '',
				plDate: '',
				plDept: '',
				rPrice: '',
				rNum: '',
				rDate: '',
				handler: '',
				deptName: '',
				typeName: ''
			}
		}
	},
	methods: {
		submit() {
			if (this.checkFormValue(this.plan.rNum, '请输入【购置数量】')
				&& this.checkFormValue(this.plan.rPrice, '请输入【购置价格】')
				&& this.checkFormValue(this.plan.rDate, '请选择【购置日期】')) {
				this.post("/modifyBuyPlan", this.plan, (result) => {
					if (result && result.state == 'success') {
						this.$refs.successAlert.show("设置成功")
					} else if (result && result.state == 'error') {
						this.$refs.alert.show("设置失败，请重试")
					}
				})
			}
		},
		checkFormValue(value, msg) {
			if (value == '') {
				this.$refs.alert.show(msg)
				return false
			}
			return true
		},
		success() {
			this.toBack()
		}
	},
	created() {
		let params = this.getParams()
		if (params) {
			this.plan.id = params.id
			this.plan.name = params.name
			this.plan.equipName = params.equipName
			this.plan.equipType = params.equipType
			this.plan.plPrice = params.plPrice
			this.plan.plNum = params.plNum
			this.plan.plDate = params.plDate
			this.plan.plDept = params.plDept
			this.plan.deptName = params.deptName
			this.plan.typeName = params.typeName
		}
		this.getData(this.KEY_USER, (res) => {
			if (res) {
				this.plan.handler = JSON.parse(res).id
			}
		})
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
.endbuyplan-wrapper {
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
.input,
.button {
  height: 80px;
  width: 360px;
  border-radius: 4px;
  padding-left: 20px;
  padding-right: 20px;
  line-height: 80px;
}
.button {
  background-color: orangered;
  justify-content: center;
  align-items: center;
  margin-left: 195px;
  margin-top: 80px;
}
.button-text {
  color: #ffffff;
  font-size: 36px;
  text-align: center;
}
.required {
  height: 80px;
  line-height: 80px;
  position: absolute;
  right: 60px;
  color: orangered;
}
</style>
