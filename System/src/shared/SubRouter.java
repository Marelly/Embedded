package shared;

public interface SubRouter {
    Object route(String subPath, Params p);
}