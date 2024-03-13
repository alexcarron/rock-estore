package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Cart;

/**
 * Defines the interface for Cart object persistence
 *
 * @author Ryan Lembo-Ehms
 */
public interface CartDAO {
    /**
     * Retrieves a {@linkplain Cart cart} with the given id
     *
     * @param id The id of the {@link Cart cart} to get
     *
     * @return a {@link Cart cart} object with the matching id
     * <br>
     * null if no {@link Cart cart} with a matching id is found
     *
     * @throws IOException if an issue with underlying storage
     */
    Cart getCart(int id) throws IOException;
}
