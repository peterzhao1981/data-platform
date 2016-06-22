package com.mode.base;

/**
 * A JSON view representation for annotating different entity classes so that we can filter the
 * fields of JSON responses.
 *
 * A Summary view may contain very few attributes that give a summary of certain business object.
 *
 * A Detail view may the summary fields plus more additional attributes that are necessary to
 * describe a business entity.
 *
 * Created by zhaoweiwei on 16/3/21.
 */
public class View {
    public interface Summary {};
    public interface Detail extends Summary {};
}