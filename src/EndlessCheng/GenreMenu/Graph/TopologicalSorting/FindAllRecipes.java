package EndlessCheng.GenreMenu.Graph.TopologicalSorting;

import java.util.*;

public class FindAllRecipes {

    // https://leetcode.cn/problems/find-all-possible-recipes-from-given-supplies/solutions/1176904/go-tuo-bu-pai-xu-by-endlesscheng-lnlg/
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> degree = new HashMap<>();
        for (int i = 0; i < recipes.length; i++) {
            String recipe = recipes[i];
            List<String> ingredientList = ingredients.get(i);
            for (String ingredient : ingredientList) { // 從這道菜的原材料向這道菜連邊
                graph.putIfAbsent(ingredient, new ArrayList<>());
                graph.get(ingredient).add(recipe);
            }
            degree.put(recipe, ingredientList.size());
        }
        List<String> res = new ArrayList<>();
        Queue<String> queue = new LinkedList<>(Arrays.asList(supplies)); // 拓撲排序（用初始原材料當隊列）
        while (!queue.isEmpty()) {
            String supply = queue.poll();
            if (graph.containsKey(supply)) {
                for (String recipe : graph.get(supply)) {
                    degree.put(recipe, degree.get(recipe) - 1);
                    if (degree.get(recipe) == 0) { // 這道菜的所有原材料我們都有
                        queue.offer(recipe);
                        res.add(recipe);
                    }
                }
            }
        }
        return res;
    }

}
