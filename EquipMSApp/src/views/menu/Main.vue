<template>
<div class="main-wrapper">
    <div class="tabs" :style="{left:curBar*-750+'px'}">
        <div class="tab">
            <home></home>
        </div>
        <div class="tab">
            <menu></menu>
        </div>
        <div class="tab">
            <me></me>
        </div>
    </div>
    <div class="navbar-wrapper">
	  <div class="bar"  v-for="(bar, i) in bars" :key="i" @click="curBar=i">
			<text class="iconfont bar-icon">{{getIcon(i,bar)}}</text>
    	    <text class="bar-text">{{bar.text}}</text>
	  </div>
    </div>
</div>
</template>
<script>
import Home from '@/views/menu/Home'
import Menu from '@/views/menu/Menu'
import Me from '@/views/menu/Me'
import he from 'he'
export default {
    data() {
        return {
            bars: [
                { name: 'Home', text: '首页', icon: '&#xe60e;', activeIcon: '&#xe610;' },
                { name: 'Menu', text: '菜单', icon: '&#xe86f;', activeIcon: '&#xe613;' },
                { name: 'Me', text: '我的', icon: '&#xe6b8;', activeIcon: '&#xe625;' }
            ],
            curBar: 1
        }
    },
    methods: {
        getIcon(i, bar) {
            if (this.curBar == i) {
                return he.decode(bar.activeIcon)
            } else {
                return he.decode(bar.icon)
            }
        },
        route(to) {
            this.redirect(to)
        }
    },
    components: {
        Home,
        Menu,
        Me
    }
}
</script>
<style>
.iconfont {
  font-family: iconfont;
}
.main-wrapper {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
.tabs {
  position: absolute;
  top: 0;
  bottom: 100px;
  flex: 1;
  flex-direction: row;
}
.tab {
  width: 750px;
  flex-direction: row;
  flex-wrap: wrap;
}
.navbar-wrapper {
  position: absolute;
  height: 100px;
  bottom: 0;
  left: 0;
  right: 0;
  flex-direction: row;
  border-top-width: 2px;
  border-top-color: rgba(128, 128, 128, 0.7);
}
.bar {
  flex: 1;
  align-items: center;
  justify-content: center;
}
.bar-text,
.bar-icon {
  color: #333;
}
.bar-icon {
  font-size: 46px;
}
.bar-text {
  font-size: 24px;
}
</style>



