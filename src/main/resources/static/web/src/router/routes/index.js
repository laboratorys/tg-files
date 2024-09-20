export const basicRoutes = [
  {
    name: "index",
    path: "/",
    //component: () => import("@/components/layout/Index.vue"),
    meta: {
      title: "图床",
    },
    children: [
      {
        name: "upload",
        path: "",
        component: () => import("@/components/page/public/Upload.vue"),
      },
    ],
  },
  {
    name: "admin",
    path: "/admin",
    //component: () => import("@/components/layout/Index.vue"),
    redirect: "/admin/files",
    meta: {
      title: "后台管理",
      requiresAuth: true,
    },
    children: [
      {
        name: "storage",
        path: "/admin/storage",
        component: () => import("@/components/page/admin/Storage.vue"),
      },
      {
        name: "files",
        path: "/admin/files",
        component: () => import("@/components/page/admin/Files.vue"),
      },
      {
        name: "config",
        path: "/admin/config",
        component: () => import("@/components/page/admin/Config.vue"),
      },
    ],
  },
];
