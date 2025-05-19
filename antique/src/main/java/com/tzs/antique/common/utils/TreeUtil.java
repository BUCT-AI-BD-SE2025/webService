package com.tzs.antique.common.utils;


import com.tzs.antique.common.domain.Tree;
import com.tzs.antique.common.domain.VueRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TreeUtil {
    private final static Integer TOP_NODE_ID = 0;

    /**
     * 用于构建菜单或部门树
     *
     * @param nodes nodes
     * @param <T>   <T>
     * @return <T> Tree<T>
     */
    public static <T> Tree<T> build(List<Tree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<>();
        AtomicReference<Integer> parentNodeId= new AtomicReference<>(0);
        nodes.forEach(node -> {
            int pid = node.getParentId();
            if (TOP_NODE_ID == pid||parentNodeId.get().equals(pid)) {
                topNodes.add(node);
                return;
            }
            for (Tree<T> n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null)
                        n.initChildren();
                    n.getChildren().add(node);
                    node.setHasParent(true);
                    n.setHasChildren(true);
                    n.setHasParent(true);
                    return;
                }
            }
            if (topNodes.isEmpty()){
                topNodes.add(node);
                parentNodeId.set(node.getParentId());
            }

        });


        Tree<T> root = new Tree<>();
        root.setId("0");
        root.setParentId(0);
        root.setHasParent(false);
        root.setHasChildren(true);
        root.setChildren(topNodes);
        root.setText("root");
        return root;
    }
    /**
     * 构造前端路由
     *
     * @param routes routes
     * @param <T>    T
     * @return ArrayList<VueRouter < T>>
     */
    public static <T> ArrayList<VueRouter<T>> buildVueRouter(List<VueRouter<T>> routes) {
        if (routes == null) {
            return null;
        }
        List<VueRouter<T>> topRoutes = new ArrayList<>();
        VueRouter<T> router = new VueRouter<>();
//        router.setName("系统主页");
//        router.setPath("/home");
//        router.setComponent("HomePageView");
//        router.setIcon("home");
//        router.setChildren(null);
//        router.setMeta(new RouterMeta(false, true));
//        topRoutes.add(router);

        routes.forEach(route -> {
            Integer parentId = route.getParentId();
            if (parentId == null || TOP_NODE_ID.equals(parentId)) {
                topRoutes.add(route);
                return;
            }
            for (VueRouter<T> parent : routes) {
                String id = parent.getId();
                if (id != null && id.equals(parentId)) {
                    if (parent.getChildren() == null)
                        parent.initChildren();
                    parent.getChildren().add(route);
                    parent.setHasChildren(true);
                    route.setHasParent(true);
                    parent.setHasParent(true);
                    return;
                }
            }
        });

        ArrayList<VueRouter<T>> list = new ArrayList<>();
        VueRouter<T> root = new VueRouter<>();
        root.setComponent("dashboard/Index");
        root.setPath("/");
        root.setChildren(topRoutes);
        list.add(root);

        return list;
    }
}
