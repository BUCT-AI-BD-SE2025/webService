<template>
  <v-app :style="{ 'background-color': '#f6f7fc' }">
    <dashboard-core-app-bar />

    <dashboard-core-view />
    <!-- <app-member v-if="!appbar" /> -->

    <!-- <dashboard-core-settings /> -->
    <!-- <dashboard-core-footer /> -->
  </v-app>
</template>

<script>
import db from "../../utils/localstorage";
export default {
  name: "DashboardIndex",
  components: {
    DashboardCoreAppBar: () => import("./components/core/AppBar"), //头部
    DashboardCoreFooter: () => import("./components/core/Footer"), //头部
    // AppMember: () => import('./components/core/AppMember'),//头部
    // AppMember: () => import('./components/core/AppMember'),//头部
    // DashboardCoreDrawer: () => import("./components/core/Drawer"), // 左侧
    //  DashboardCoreSettings: () => import('./components/core/Settings'),
    DashboardCoreView: () => import("./components/core/View"), //底部
  },

  data: () => ({}),
  //db.get('ROLES'),
  created() {},
};
</script>
<style>
html,
body {
  height: 100%;
}
.v-app {
  display: flex;
  flex-direction: column;
  min-height: 100%;
}
.v-main {
  flex: 1;
}
</style>
