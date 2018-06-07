<template>
<div class="buyplan-wrapper">
	<mini-bar title="添加购置计划" back="true"></mini-bar>
  <div class="main">
  <div class="row">
		<text class="label" >计划名称：</text>
		<input class="input" type="text" v-model="plan.name" autofocus="true">
		<text class="required">*</text>
	</div>
	<div class="row">
		<text class="label">设备名称：</text>
		<input class="input" type="text" v-model="plan.equipName">
		<text class="required">*</text>
	</div>
	<div class="row">
		<text class="label">设备种类：</text>
		<button @click="showSelectList('typelist')" class="input" style="justify-content:center"><text>{{plan.typename}}</text></button>
    	<text class="required">*</text>
	</div>
	<div class="row">
		<text class="label">预计单价：</text>
		<input class="input" type="number" v-model="plan.plPrice">
    <text style="height:80px;line-height:80px">元</text>
    <text class="required">*</text>
	</div>
	<div class="row">
		<text class="label">预购置数量：</text>
		<input class="input" type="number" v-model="plan.plNum">
    <text class="required">*</text>
	</div>
		<div class="row">
			<text class="label">预购置日期：</text>
			<input class="input" type="date" v-model="plan.plDate">
			<text class="required">*</text>
		</div>
    <button @click="submit" class="button"><text class="button-text">确认</text></button>
  </div>
  <list-select :list="typeList" title="选择设备种类" valStr="name" ref="typelist" @listSelected="listSelected"></list-select>
  <alert ref="alert"></alert>
</div>
</template>
<script>
import { MiniBar, Alert, ListSelect } from '@/views/components'
export default {
	data: () => ({
		plan: {
			name: '',
			equipName: '',
			equipType: '',
			plDept: '',
			plPrice: '',
			plNum: '',
			plDate: '',
			typename: ''
		},
		typeList: {},
	}),
	methods: {
		submit() {
			if (this.checkFormValue(this.plan.name, '请输入【计划名称】')
				&& this.checkFormValue(this.plan.equipName, '请输入【设备名称】')
				&& this.checkFormValue(this.plan.equipType, '请选择【设备种类】')
				&& this.checkFormValue(this.plan.plDept, '请选择【预购置部门】')
				&& this.checkFormValue(this.plan.plPrice, '请输入【预计单价】')
				&& this.checkFormValue(this.plan.plNum, '请输入【预购置数量】')
				&& this.checkFormValue(this.plan.plDate, '请选择【预购置日期】')) {
				this.post("/addBuyPlan", this.plan, (result) => {
					if (result && result.state == 'success') {
						this.$refs.alert.show("购置计划添加成功")
						this.plan.name = this.plan.equipName = this.plan.equipType
							= this.plan.plDept = this.plan.plPrice = this.plan.plNum
							= this.plan.plDate = this.plan.deptname = this.plan.typename = ''
					} else if (result && result.state == 'error') {
						this.$refs.alert.show("购置计划添加失败，请重新添加")
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
		listSelected(e) {
			this.plan.equipType = e.obj.id
			this.plan.typename = e.obj.name
		},
		showSelectList(key) {
			this.select_flag = key
			this.$refs[key].show()
		},
	},
	created() {
		this.getData(this.KEY_USER, (res) => {
			if (res) {
				this.plan.plDept = JSON.parse(res).dept
			}
		})
		this.getData(this.KEY_EQUIPTYPE, (res) => {
			if (res) {
				this.typeList = JSON.parse(res)
			}
		})
	},
	components: {
		MiniBar,
		Alert,
		ListSelect
	}
}
</script>
<style scoped>
.iconfont {
  font-family: iconfont;
}
.buyplan-wrapper {
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
  top: 140px;
}
.row {
  flex-direction: row;
  background-color: #fff;
  padding-left: 60px;
  border-bottom-width: 1px;
  border-bottom-color: rgba(128, 128, 128, 0.5);
}
.label {
  height: 80px;
  width: 200px;
  text-align: left;
  line-height: 80px;
}
.input,
.button {
  height: 80px;
  width: 360px;
  border-radius: 4px;
  padding-left: 20px;
  padding-right: 20px;
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


