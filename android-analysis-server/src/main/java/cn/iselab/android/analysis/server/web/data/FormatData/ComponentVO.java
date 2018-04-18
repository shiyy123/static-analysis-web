package cn.iselab.android.analysis.server.web.data.FormatData;

/**
 * Descriptor: the class of the component type of vulnerability, this type of vulnerability only have two element
 * component : the type of the component (activity, service, provider and receiver)
 * location : the name(location) of the component
 */
public class ComponentVO extends FormatFileVO {
    private String component;
    private String location;

    public ComponentVO() {
    }

    public ComponentVO(String component, String location) {
        this.component = component;
        this.location = location;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
