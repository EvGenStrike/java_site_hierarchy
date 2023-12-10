package _11._2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;

public class Main{
    public static void main(String[] args) {
        String url = "https://school5prv.ru/";
        String pathToSiteMapFile = "src\\_11\\_2\\siteHierarchy.txt";
        SiteHierarchy siteHierarchy = new SiteHierarchy(url);
        SiteHierarchyBuilder task = new SiteHierarchyBuilder(siteHierarchy);

        new ForkJoinPool().invoke(task);

        try {
            FileOutputStream stream = new FileOutputStream(pathToSiteMapFile);
            String siteMapFile = createSiteMapString(siteHierarchy, 0);
            stream.write(siteMapFile.getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createSiteMapString(SiteHierarchy siteHierarchy, int indent) {
        String tab = String.join("", Collections.nCopies(indent, "\t"));
        StringBuilder result = new StringBuilder(tab + siteHierarchy.getUrl());
        siteHierarchy
                .getSiteMapChildren()
                .forEach(child -> result.append("\n")
                                    .append(createSiteMapString(child, indent + 1))
                );
        return result.toString();
    }
}