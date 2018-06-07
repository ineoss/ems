<template>
<div class="menu-wrapper">
    <mini-bar title="菜单" @scanClick="scan"></mini-bar>
    <div class="main">
        <div class="active" :style="{top:activeTab*100+'px'}"></div>
        <div class="menu-bar">
            <list>
                 <cell v-for="(menu,i) in menus" :key="i">
                    <text @click="trans(i)" class="menu-bar-text" :style="{color:i==activeTab?'goldenrod':''}" >{{menu.name}}</text>
                </cell>
            </list>
        </div>
        <div class="container">
            <div class="tab-panels" ref="tab">
                <div class="panel" v-for="(menu, pi) in menus" :key="pi" >
                    <div class="submenu" v-for="(submenu, ps) in menu.submenus" :key="ps" @click="jump(submenu.page)" >
                        <text class="icon iconfont" :style="{color:submenu.color}">{{getIcon(submenu.icon)}}</text>
                        <text class="text">{{submenu.name}}</text>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<script>
import MiniBar from '@/views/components/MiniBar'
import he from 'he'
export default {
    methods: {
        jump(page) {

            this.jumpTo(page)
        },
        getIcon(icon) {
            return he.decode(icon)
        },
        trans(i) {
            this.activeTab = i
            this.getModule('animation').transition(this.$refs.tab, {
                styles: {
                    transform: `translateX(${i * -590}px)`
                },
                duration: 200, // ms
                timingFunction: 'ease-in',
                delay: 0 // ms
            }, function () {
            });
        }
    },
    data() {
        return {
            activeTab: 0,
            menus: [
                {
                    name: '设备管理', page: 'EquipmentManagement',
                    submenus: [
                        { name: '添加设备', page: 'AddEquip', icon: '&#xe75e;', color: 'olivedrab' },
                        { name: '设备入库', page: 'InStock', icon: '&#xe659;', color: 'goldenrod' },
                        { name: '设备出库', page: 'OutStock', icon: '&#xe602;', color: 'goldenrod' },
                        { name: '库存列表', page: 'StockList', icon: '&#xe631;', color: 'mediumvioletred' },
                        { name: '设备列表', page: 'EquipList', icon: '&#xea35;', color: 'mediumvioletred' }
                    ]
                },
                {
                    name: '购置管理', page: 'BuyManagement',
                    submenus: [
                        { name: '添加购置计划', page: 'AddBuyPlan', icon: '&#xe75e;', color: 'goldenrod' },
                        { name: '购置计划列表', page: 'BuyPlanList', icon: '&#xe62e;', color: 'green' },
                        { name: '购置历史', page: 'BuyHistory', icon: '&#xe635;', color: 'green' },
                    ]
                },
                {
                    name: '借用管理', page: 'BorrowManagement',
                    submenus: [
                        { name: '可借用设备列表', page: 'CanBorrowList', icon: '&#xe618;', color: 'goldenrod' },
                        { name: '设备借用审核', page: 'BorrowAuditingList', icon: '&#xe649;', color: 'green' },
                        { name: '设备归还', page: 'BorrowedList', icon: '&#xe659;', color: 'green' },
                        { name: '我的申请', page: 'MyBorrowApply', icon: '&#xe63b;', color: 'green' },
                    ]
                },
                {
                    name: '维修管理', page: 'RepaireManagement',
                    submenus: [
                        { name: '维修信息添加', page: 'AddRepaire', icon: '&#xe63a;', color: 'goldenrod' },
                        { name: '设备维修历史', page: 'RepaireHistory', icon: '&#xe62e;', color: 'green' },
                        { name: '近期需检修设备', page: 'NeedOverhaul', icon: '&#xe624;', color: 'goldenrod' },
                    ]
                },
                {
                    name: '报废管理', page: 'GiveupManagement',
                    submenus: [
                        { name: '设备报废', page: 'AddGiveup', icon: '&#xe606;', color: 'goldenrod' },
                        { name: '报废设备列表', page: 'GiveupList', icon: '&#xe635;', color: 'green' },
                    ]
                },
                {
                    name: '用户管理', page: 'UserManagement',
                    submenus: [
                        { name: '添加用户', page: 'AddUser', icon: '&#xe630;', color: 'orange' },
                        { name: '用户列表', page: 'UserList', icon: '&#xe634;', color: 'olivedrab' },
                    ]
                },
            ]
        }
    },
    components: {
        MiniBar
    }
}
</script>

<style scoped>
.iconfont {
  font-family: iconfont;
}
.menu-wrapper {
  position: absolute;
  bottom: 0;
  top: 0;
  left: 0;
  right: 0;
}
.main {
  position: absolute;
  bottom: 0;
  top: 100px;
  left: 0;
  right: 0;
}
.menu-bar {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 160px;
  border-right-width: 1px;
  border-right-color: #808080;
  align-items: center;
}
.menu-bar-text {
  width: 160px;
  height: 100px;
  text-align: center;
  line-height: 100px;
}
.active {
  height: 100px;
  width: 4px;
  transition: top 0.2s ease-in-out;
  background-color: goldenrod;
}
.container {
  position: absolute;
  left: 160px;
  top: 0;
  bottom: 0;
  right: 0;
  width: 590px;
  overflow: hidden;
}
.tab-panels {
  position: absolute;
  top: 0;
  bottom: 0;
  flex: 1;
  flex-direction: row;
}
.panel {
  width: 590px;
  padding-top: 40px;
  flex-direction: row;
  flex-wrap: wrap;
}
.submenu {
  width: 295px;
  align-items: center;
  justify-content: center;
  height: 180px;
}
.submenu:active {
  background-color: #eee8aa;
}
.icon {
  font-weight: bold;
  font-size: 64px;
}
.text {
  font-size: 28px;
  margin-top: 10px;
}
</style>

