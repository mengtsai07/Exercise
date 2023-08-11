const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/IndexPage.vue") }],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
  {
    path: "/petpet",
    name: "petpet",
    component: () => import("../pages/ShelterAnimal.vue"),
  },
  {
    path: "/addAnimal",
    name: "addAnimal",
    component: () => import("../pages/AddAnimal.vue"),
  },
  {
    path: "/editAnimal/:id",
    name: "editAnimal",
    component: () => import("../pages/EditAnimal.vue"),
    props: (route) => route.params,
  },
];

export default routes;
