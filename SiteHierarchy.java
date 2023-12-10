package _11._2;

import java.util.concurrent.CopyOnWriteArrayList;

public class SiteHierarchy {
    private final String url;
    private final CopyOnWriteArrayList<SiteHierarchy> siteHierarchyChildren;

    public SiteHierarchy(String url) {
        siteHierarchyChildren = new CopyOnWriteArrayList<>();
        this.url = url;
    }

    public void addChildren(SiteHierarchy children) {
        siteHierarchyChildren.add(children);
    }

    public CopyOnWriteArrayList<SiteHierarchy> getSiteMapChildren() {
        return siteHierarchyChildren;
    }

    public String getUrl() {
        return url;
    }
}
