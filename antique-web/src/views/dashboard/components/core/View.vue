<template>
  <v-main>
    <router-view />
    <v-spacer />
    <dashboard-core-footer />
  </v-main>
</template>

<script>
export default {
  name: "DashboardCoreView",

  components: {
    DashboardCoreFooter: () => import("./Footer"),
  },
};
</script>

<style>
html,
body {
  height: 100%;
  margin: 0;
}

.v-application {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 确保应用至少占据整个视口高度 */
}

.v-main {
  flex-grow: 1; /* 让主内容区域扩展以填充可用空间 */
}
</style>
