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
    created() {
        this.getData(this.KEY_USER, (res) => {
            if (res) {
                let role = JSON.parse(res).role
                this.get("/getMights", { role: role }, (result) => {
                    if (result && result.state == "success") {
                        this.menus = result.data
                    }
                })
            }
        })
    },
    data() {
        return {
            activeTab: 0,
            menus: []
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

