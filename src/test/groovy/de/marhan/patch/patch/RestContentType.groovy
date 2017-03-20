package de.marhan.patch.patch

import org.apache.commons.collections.iterators.ArrayIterator

enum RestContentType {

    ANY("*/*"),
    TEXT("text/plain"),
    JSON("application/json", "application/javascript", "text/javascript"),
    MERGE_PATCH_JSON("application/merge-patch+json"),
    XML("application/xml", "text/xml", "application/xhtml+xml", "application/atom+xml"),
    HTML("text/html"),
    URLENC("application/x-www-form-urlencoded"),
    BINARY("application/octet-stream");

    private final String[] ctStrings;

    public String[] getContentTypeStrings() { return ctStrings; }

    @Override
    public String toString() { return ctStrings[0]; }

    /**
     * Builds a string to be used as an HTTP <code>Accept</code> header
     * value, i.e. "application/xml, text/xml"
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getAcceptHeader() {
        Iterator<String> iter = new ArrayIterator(ctStrings);
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            sb.append(iter.next());
            if (iter.hasNext()) sb.append(", ");
        }
        return sb.toString();
    }

    private RestContentType(String... contentTypes) {
        this.ctStrings = contentTypes;
    }

}
