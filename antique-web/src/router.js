import Vue from "vue";
import Router from "vue-router";
import request from "@/utils/request";
import db from "@/utils/localstorage";

Vue.use(Router);

const originalPush = Router.prototype.push;
Router.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject);
  return originalPush.call(this, location).catch((err) => err);
};

const router = new Router({
  mode: "hash",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/login",
      name: "Login",
      component: () => import("@/login/login"),
    },
    {
      path: "/",
      component: () => import("@/views/dashboard/Index"),
      children: [
        {
          path: "/",
          name: "antique",
          component: () => import("@/views/antique/antique_client"),
        },
        {
          path: "/antique-details",
          name: "antique-details",
          component: () => import("@/views/antique/antique_details"),
        },
      ],
    },
  ],
});

const whiteList = ["/login"];
let asyncRouter;
router.beforeEach((to, from, next) => {
  if (whiteList.indexOf(to.path) !== -1) {
    next();
    return;
  }
  let token = db.get("USER_TOKEN");
  let user = db.get("USER");
  let userRouter = get("USER_ROUTER");
  if (token.length && user) {
    if (!asyncRouter) {
      if (!userRouter) {
        request.get(`menu`).then((res) => {
          asyncRouter = res.data;
          save("USER_ROUTER", asyncRouter);
          go(to, next);
        });
      } else {
        asyncRouter = userRouter;
        go(to, next);
      }
    } else {
      next();
    }
  } else {
    next("/login");
  }
});

function go(to, next) {
  asyncRouter = filterAsyncRouter(asyncRouter);
  router.addRoutes(asyncRouter);
  next({ ...to, replace: true });
}

function save(name, data) {
  localStorage.setItem(name, JSON.stringify(data));
}

function get(name) {
  return JSON.parse(localStorage.getItem(name));
}

function filterAsyncRouter(routes) {
  return routes.filter((route) => {
    let component = route.component;
    if (component) {
      route.component = view(component);
      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children);
      }
      return true;
    }
  });
}

function view(path) {
  return function (resolve) {
    // @后面必须跟文件夹，不可去掉
    import(`@/views/${path}.vue`).then((mod) => {
      resolve(mod);
    });
  };
}

export default router;
