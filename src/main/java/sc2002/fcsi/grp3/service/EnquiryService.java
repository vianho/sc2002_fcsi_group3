package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;

public class EnquiryService {
    private final DataStore db;

    public EnquiryService(DataStore db) {
        this.db = db;
    }

//    public List<Enquiry> getEnquiries() {
//        return db.getEnquiries();
//    }
}
