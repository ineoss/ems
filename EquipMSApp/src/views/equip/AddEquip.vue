<template>
<div class="addequip-wrapper">
	<mini-bar title="添加设备" back="true"></mini-bar>
  <div class="main">
    <div class="row">
			<text class="label" >设备名称：</text>
			<input class="input" type="text" v-model="equip.name">
			<text class="required">*</text>
		</div>
		<div class="row">
			<text class="label">生产厂家：</text>
			<button @click="showSelectList('factorylist')" class="input" style="justify-content:center"><text>{{equip.factoryname}}</text></button>
			<text class="required">*</text>
		</div>
		<div class="row">
			<text class="label">购置日期：</text>
			<input class="input" type="date" v-model="equip.buyDate">
			<text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">购置价格：</text>
			<input class="input" type="number" v-model="equip.price">
      <text style="height:80px;line-height:80px">元</text>
      <text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">使用年限：</text>
			<input class="input" type="number" v-model="equip.useYear">
      <text style="height:80px;line-height:80px">年</text>
			<text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">购置部门：</text>
			<button @click="showSelectList('deptlist')" class="input" style="justify-content:center"><text>{{equip.deptname}}</text></button>
      <text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">设备分类：</text>
			<button @click="showSelectList('typelist')" class="input" style="justify-content:center"><text>{{equip.typename}}</text></button>
      <text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">设备状态：</text>
			<button @click="showSelectList('statelist')" class="input" style="justify-content:center"><text>{{equip.statename}}</text></button>
      <text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">存放位置：</text>
			<input class="input" type="text" v-model="equip.pos">
      <text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">检修周期：</text>
			<input class="input" type="number" v-model="equip.repaireCycle">
      <text style="height:80px;line-height:80px">天</text>
      <text class="required">*</text>
		</div>
    <button @click="submit" class="button"><text class="button-text">确认</text></button>
  </div>
  <list-select :list="factoryList" title="选择生产厂家" valStr="factoryname" ref="factorylist" @listSelected="listSelected"></list-select>
  <list-select :list="deptList" title="选择部门" valStr="deptname" ref="deptlist" @listSelected="listSelected"></list-select>
  <list-select :list="typeList" title="选择设备种类" valStr="name" ref="typelist" @listSelected="listSelected"></list-select>
  <list-select :list="stateList" title="选择设备状态" valStr="name" ref="statelist" @listSelected="listSelected"></list-select>
  <alert ref="alert"></alert>
</div>
</template>
<script>
import { MiniBar, Alert, ListSelect } from '@/views/components'
export default {
  data: () => ({
    equip: {
      name: '',
      factory: '',
      buyDate: '',
      price: '',
      buyDept: '',
      useYear: '',
      equipType: '',
      pos: '',
      state: '2004',
      factoryname: '',
      deptname: '',
      typename: '',
      statename: '待入库',
      repaireCycle: ''
    },
    factoryList: {},
    deptList: {},
    typeList: {},
    stateList: {},
    select_flag: 'factorylist'
  }),
  methods: {
    submit() {
      if (this.checkFormValue(this.equip.name, '请输入设备名')
        && this.checkFormValue(this.equip.factory, '请选择生产厂家')
        && this.checkFormValue(this.equip.buyDate, '请选择购置日期')
        && this.checkFormValue(this.equip.price, '请输入价格')
        && this.checkFormValue(this.equip.buyDept, '请选择购置部门')
        && this.checkFormValue(this.equip.useYear, '请输入使用年限')
        && this.checkFormValue(this.equip.equipType, '请选择设备种类')
        && this.checkFormValue(this.equip.pos, '请输入存放位置')) {
        this.post("/addEquipment", this.equip, (result) => {
          if (result && result.state == 'success') {
            let msg = {
              id: result.data.id,
              assetNo: result.data.assetNo,
              name: this.equip.name
            }
            this.getModule('event').createQRCode(JSON.stringify(msg))
            this.$refs.alert.show("添加成功")
          } else if (result && result.state == 'error') {
            this.$refs.alert.show("添加失败，请重新添加")
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
      if (this.select_flag == 'factorylist') {
        this.equip.factory = e.obj.id
        this.equip.factoryname = e.obj.factoryname
      } else if (this.select_flag == 'deptlist') {
        this.equip.buyDept = e.obj.id
        this.equip.deptname = e.obj.deptname
      } else if (this.select_flag == 'typelist') {
        this.equip.equipType = e.obj.id
        this.equip.typename = e.obj.name
      } else if (this.select_flag == 'statelist') {
        this.equip.state = e.obj.id
        this.equip.statename = e.obj.name
      }
    },
    showSelectList(key) {
      this.select_flag = key
      this.$refs[key].show()
    },
  },
  created() {
    this.getData(this.KEY_FACTORYLIST, (res) => {
      if (res) {
        this.factoryList = JSON.parse(res)
      }
    })
    this.getData(this.KEY_DEPTLIST, (res) => {
      if (res) {
        this.deptList = JSON.parse(res)
      }
    })
    this.getData(this.KEY_EQUIPTYPE, (res) => {
      if (res) {
        this.typeList = JSON.parse(res)
      }
    })
    this.getData(this.KEY_EQUIPSTATE, (res) => {
      if (res) {
        this.stateList = JSON.parse(res)
      }
    })
    var date = new Date();
    this.equip.buyDate = '' + date.getFullYear() + "-"+(date.getMonth() + 1) + "-" + date.getDate()
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
.addequip-wrapper {
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
  width: 180px;
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
.qr {
  background-color: #0190c0;
  margin-top: 20px;
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


