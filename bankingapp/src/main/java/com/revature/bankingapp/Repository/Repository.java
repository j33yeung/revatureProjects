package com.revature.bankingapp.Repository;

import java.util.List;

public interface Repository<ID, E>{

    List<E> getAll();
    E getById(ID id);
    ID save(E obj);
    void delete(E obj);

}
