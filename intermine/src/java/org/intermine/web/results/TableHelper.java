package org.intermine.web.results;

/*
 * Copyright (C) 2002-2004 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.List;

import org.intermine.objectstore.ObjectStore;
import org.intermine.objectstore.ObjectStoreException;
import org.intermine.objectstore.query.Query;

/**
 * Helper methods for the PagedTable object.
 *
 * @author Kim Rutherford
 */

public abstract class TableHelper
{
    /**
     * Add a PagedTable object to the session for the given query (by executing the Query and
     * creating a PagedResults object).  The PagedResults object is stored as RESULTS_TABLE in the
     * session.
     *
     * @param os the ObjectStore against which to run the Query
     * @param query the Query to create the PagedTable for
     * @param view the list of paths to SELECT
     * @return a PagedResults object for the argument Query
     * @throws ObjectStoreException if an error occurs in the underlying ObjectStore
     */
    public static PagedResults makeTable(ObjectStore os, Query query, List view)
        throws ObjectStoreException {
        PagedResults pr = new PagedResults(os.execute(query), view);

        // call this so that if an exception occurs we notice now rather than in the JSP code
        pr.getResults().size();

        return pr;
    }
}
