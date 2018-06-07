<template>
<div class="equipdetail-wrapper">
	<mini-bar title="设备详细信息" back="true"></mini-bar>
	<div class="main">
	<list>
		<refresh class="refresh" @refresh="onrefresh"  :display="refreshing ? 'show' : 'hide'">
      		<text class="indicator-text">刷新中 ...</text>
      		<loading-indicator class="indicator"></loading-indicator>
    </refresh>
		<cell class="cell">
		<image :src="image" class="image"/>
		</cell>
		<cell class="cell">
		<div class="row">
			<text class="label">设备名称：</text>
			<text class="input">{{equip.name}}</text>
		</div>
		<div class="row">
			<text class="label">资产编号：</text>
			<text class="input">{{equip.assetNo}}</text>
		</div>
		<div class="row">
			<text class="label">设备分类：</text>
			<text class="input">{{equip.typename}}</text>
		</div>
		<div class="row">
			<text class="label">设备状态：</text>
			<text class="input">{{equip.statename}}</text>
		</div>
		<div class="row">
			<text class="label">使用年限：</text>
			<text class="input">{{equip.useYear}}年</text>
		</div>
		<div class="row">
			<text class="label">购置价格：</text>
			<text class="input">{{equip.price}}元</text>
		</div>
		<div class="row">
			<text class="label">购置日期：</text>
			<text class="input">{{equip.buyDate}}</text>
		</div>
		<div class="row">
			<text class="label">购置部门：</text>
			<text class="input">{{equip.deptname}}</text>
		</div>
		<div class="row">
			<text class="label">生产厂家：</text>
			<text class="input">{{equip.factoryname}}</text>
		</div>
			<div class="row">
			<text class="label">检修周期：</text>
			<text class="input">{{equip.repaireCycle}}天</text>
		</div>
		<div class="row">
			<text class="label">存放位置：</text>
			<text class="input">{{equip.pos}}</text>
		</div>
		<div class="row">
			<text style=" height: 80px;width: 300px;line-height: 80px;">该类设备平均寿命：</text>
			<text class="input">{{equip.age}}天</text>
		</div>
		<div class="button-group" v-if="equip.buyDept==userDept">
			<button><text @click="delEquipAlert" class="util" style="background-color:rgba(255,0,0,0.5)">删除设备</text></button>
			<button><text @click="modifyEquip" class="util" style="background-color:rgb(0, 191, 255)">修改设备信息</text></button>
		</div>
		</cell>
		<alert ref="delAlert" @dialogConfirmBtnClick="delEquip" :single="false"></alert>
		<alert ref="delSuccessAlert" @dialogConfirmBtnClick="delSuccess"></alert>
		<alert ref="alert"></alert>
	</list>
	</div>
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
			refreshing: false,
			userDept: ''
		}
	},
	methods: {
		onrefresh(event) {
			this.refreshing = true
			this.get('/getEquipmentInfo', { id: this.equip.id }, (result) => {
				if (result && result.state == 'success') {
					this.equip = result.data
				}
				this.refreshing = false
			})
		},
		delEquipAlert() {
			this.$refs['delAlert'].show('删除设备后不可恢复，确定要删除此设备吗？')
		},
		delEquip() {
			this.post('/delEquip', { id: this.equip.id, name: this.equip.name }, (result) => {
				if (result && result.state == 'success') {
					this.$refs['delSuccessAlert'].show('删除成功')
				} else {
					this.$refs['alert'].show('删除失败')
				}
			})
		},
		delSuccess() {
			this.toBack()
		},
		modifyEquip() {
			this.jumpWithParams('ModifyEquip', this.equip)
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
			this.get('/getEquipmentInfo', { id: params.id }, (result) => {
						console.log(result)

				if (result && result.state == 'success') {
					this.equip = result.data
				}
			})
		}
		this.getData(this.KEY_USER, (res) => {
			if (res) {
				this.userDept = JSON.parse(res).dept
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
.equipdetail-wrapper {
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
  align-items: center;
  justify-content: center;
}
.image {
  height: 360px;
  width: 520px;
  margin-bottom: 20px;
  margin-top: 20px;
  background-color: #ffffff;
}
.row {
  flex-direction: row;
  width: 750px;
  padding-left: 60px;
  background-color: #ffffff;
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
  margin-bottom: 20px;
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
