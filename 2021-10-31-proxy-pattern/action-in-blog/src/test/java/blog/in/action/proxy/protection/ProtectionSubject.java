package blog.in.action.proxy.protection;

import blog.in.action.proxy.Client;

public interface ProtectionSubject {

    void printNormalThing(Client client);

    void printAdminThing(Client client);
}
