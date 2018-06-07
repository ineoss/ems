<template>
<div class="home-wrapper">
        <list class="main">
        <refresh class="refresh" @refresh="onrefresh"  :display="refreshing ? 'show' : 'hide'">
      		<text class="indicator-text">刷新中 ...</text>
      		<loading-indicator class="indicator"></loading-indicator>
    	</refresh>
        <cell class="cell">
        <div class="block-web">
                <web ref="typeChart" style="width: 710px; height: 500px" :src="chartSrc"></web>
                <div class="mask" @click="mask"></div>
        </div>
        </cell>
        <cell class="cell">
        <div class="block">
                <text>共有用户：{{userCount}}人</text>
                <text>共有设备：{{equipment.count}}台</text>
                <div class="divide"></div>
                <text>库存设备：{{equipment.stockCount}}台</text>
                <text>使用中设备：{{equipment.usingCount}}台</text>
                <text>设备使用率（使用中/总）：{{equipment.usingRate}}</text>
                <div class="divide"></div>
                <text>设备总资产：{{equipment.totalAsset}}元</text>
                
        </div>
        </cell>
        <cell class="cell">
        <div class="block">
                <text>报废数量最多的生产厂家：{{giveupInfo.factoryName}}</text>
                <text>共报废设备：{{giveupInfo.num}}台</text>
                <div class="divide"></div>
                <text>维修数量最多的生产厂家：{{repaireInfo.factoryName}}</text>
                <text>共维修设备：{{repaireInfo.num}}次</text>
        </div>
        </cell> 
        </list>
</div>
</template>
<script>
import config from '@/config'
let server_ip = config.server_ip
export default {
        components: {
        },
        data() {
                return {
                        userCount: '',
                        equipment: '',
                        giveupInfo: {},
                        repaireInfo: {},
                        chartSrc: server_ip + '/chart.html',
                        refreshing: false
                }
        },
        methods: {
                getUsingRate() {
                        return (parseInt(this.usingNum) / parseInt(this.equipmentCount)) * 100
                },
                init() {
                        this.get('/getUserCount', null, result => {//获取用户总数
                                if (result && result.state == 'success') {
                                        this.userCount = result.data
                                }
                        })
                        this.get('/getEquipmentCountInfo', null, result => {//获取设备各项数据
                                if (result && result.state == 'success') {
                                        this.equipment = result.data
                                }
                        })

                        this.get('/getMaxGiveupFactory', null, result => {
                                if (result && result.state == 'success') {
                                        this.giveupInfo = result.data
                                }
                        })
                        this.get('/getMaxRepaireFactory', null, result => {
                                if (result && result.state == 'success') {
                                        this.repaireInfo = result.data
                                }
                        })
                },
                onrefresh(event) {
                        this.refreshing = true
                        this.getModule('webview').reload(this.$refs.typeChart);
                        this.init()
                        setTimeout(() => {
                                this.refreshing = false
                        }, 1000)

                },
                mask() { return }
        },
        created() {
                this.init()
        }
}
</script>
<style scoped>
.home-wrapper {
  position: absolute;
  bottom: 0;
  top: 0;
  left: 0;
  right: 0;
  background-color: #f0f0f0;
}
.mask {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 500px;
}
.main {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 0;
}
.cell {
  justify-content: center;
  align-items: center;
}
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
.indicator-text {
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
.block {
  width: 730px;
  margin-left: 10px;
  border-width: 1px;
  margin-top: 10px;
  border-radius: 10px;
  padding-left: 40px;
  padding-right: 40px;
  padding-top: 20px;
  padding-bottom: 20px;
  background-color: rgba(222, 184, 135, 0.5);
}
.block-web {
  width: 730px;
  margin-left: 10px;
  border-width: 1px;
  margin-top: 10px;
  border-radius: 10px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 10px;
  padding-bottom: 10px;
  background-color: rgba(222, 184, 135, 0.5);
}
.divide {
        height:2px;
        width:650px;
        margin-top:8px;
        margin-bottom:8px;
        background-color:rgba(128, 128, 128, 0.6)

}
</style>