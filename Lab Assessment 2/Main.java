class Student {
    String uid;
    String name;
    int fineAmount;
    int currentBorrowCount;

    public Student(String uid, String name, int fineAmount, int currentBorrowCount) {
        this.uid = uid;
        this.name = name;
        this.fineAmount = fineAmount;
        this.currentBorrowCount = currentBorrowCount;
    }

    public void checkEligibility() {
        if (fineAmount > 0) {
            throw new IllegalStateException("Fine pending for UID: " + uid);
        }
        if (currentBorrowCount >= 2) {
            throw new IllegalStateException("Borrow limit reached for UID: " + uid);
        }
    }
}

class Asset {
    String assetId;
    String assetName;
    boolean available;
    int securityLevel;

    public Asset(String assetId, String assetName, boolean available, int securityLevel) {
        this.assetId = assetId;
        this.assetName = assetName;
        this.available = available;
        this.securityLevel = securityLevel;
    }

    public void checkAvailability(String uid) {
        if (!available) {
            throw new IllegalStateException("Asset not available: " + assetId);
        }
        if (securityLevel == 3 && !uid.startsWith("KRG")) {
            throw new SecurityException("Restricted asset for UID: " + uid);
        }
    }
}

class CheckoutRequest {
    String uid;
    String assetId;
    int hoursRequested;

    public CheckoutRequest(String uid, String assetId, int hoursRequested) {
        this.uid = uid;
        this.assetId = assetId;
        this.hoursRequested = hoursRequested;
    }
}

class ValidationUtil {
    public static void validateUid(String uid) {
        if (uid == null || uid.length() < 8 || uid.length() > 12 || uid.contains(" ")) {
            throw new IllegalArgumentException("Invalid UID");
        }
    }

    public static void validateAssetId(String assetId) {
        if (assetId == null || !assetId.matches("LAB-\\d+")) {
            throw new IllegalArgumentException("Invalid Asset ID");
        }
    }

    public static void validateHours(int hrs) {
        if (hrs < 1 || hrs > 6) {
            throw new IllegalArgumentException("Hours must be 1 to 6");
        }
    }
}

class AssetStore {
    Asset[] assets;
    int size;

    public AssetStore(int capacity) {
        assets = new Asset[capacity];
        size = 0;
    }

    public void addAsset(Asset asset) {
        assets[size++] = asset;
    }

    public Asset findAsset(String assetId) {
        for (int i = 0; i < size; i++) {
            if (assets[i].assetId.equals(assetId)) {
                return assets[i];
            }
        }
        throw new NullPointerException("Asset not found: " + assetId);
    }

    public void markBorrowed(Asset a) {
        if (!a.available) {
            throw new IllegalStateException("Asset already borrowed: " + a.assetId);
        }
        a.available = false;
    }
}

class AuditLogger {
    public static void log(String msg) {
        System.out.println("AUDIT: " + msg);
    }

    public static void logError(Exception e) {
        System.out.println("ERROR: " + e.getMessage());
    }
}

class CheckoutService {
    AssetStore assetStore;
    Student[] students;
    int studentSize;

    public CheckoutService(AssetStore assetStore, Student[] students, int studentSize) {
        this.assetStore = assetStore;
        this.students = students;
        this.studentSize = studentSize;
    }

    private Student findStudent(String uid) {
        for (int i = 0; i < studentSize; i++) {
            if (students[i].uid.equals(uid)) {
                return students[i];
            }
        }
        throw new NullPointerException("Student not found: " + uid);
    }

    public String checkout(CheckoutRequest req)
            throws IllegalArgumentException,
                   IllegalStateException,
                   SecurityException,
                   NullPointerException {

        ValidationUtil.validateUid(req.uid);
        ValidationUtil.validateAssetId(req.assetId);
        ValidationUtil.validateHours(req.hoursRequested);

        Student student = findStudent(req.uid);
        Asset asset = assetStore.findAsset(req.assetId);

        student.checkEligibility();
        asset.checkAvailability(req.uid);

        int hours = req.hoursRequested;

        if (hours == 6) {
            System.out.println("Max duration selected.");
        }

        if (asset.assetName.contains("Cable") && hours > 3) {
            hours = 3;
            System.out.println("Cables max 3 hours. Updated to 3.");
        }

        assetStore.markBorrowed(asset);
        student.currentBorrowCount++;

        return "TXN-" + asset.assetId + "-" + student.uid;
    }
}

public class Main {
    public static void main(String[] args) {

        Student[] students = new Student[3];
        students[0] = new Student("KRG20281", "Aditya", 0, 0);
        students[1] = new Student("STU12345", "Rahul", 100, 1);
        students[2] = new Student("KRG99999", "Simran", 0, 2);
        int studentSize = 3;

        AssetStore store = new AssetStore(3);
        store.addAsset(new Asset("LAB-101", "HDMI Cable", true, 1));
        store.addAsset(new Asset("LAB-202", "Projector", true, 3));
        store.addAsset(new Asset("LAB-303", "Extension Cable", false, 1));

        CheckoutService service = new CheckoutService(store, students, studentSize);

        CheckoutRequest r1 = new CheckoutRequest("KRG20281", "LAB-101", 4);
        CheckoutRequest r2 = new CheckoutRequest("KRG20281", "LAB-XYZ", 2);
        CheckoutRequest r3 = new CheckoutRequest("STU12345", "LAB-202", 2);

        CheckoutRequest[] requests = {r1, r2, r3};

        for (CheckoutRequest req : requests) {
            try {
                String receipt = service.checkout(req);
                System.out.println("SUCCESS: " + receipt);
            }
            catch (IllegalArgumentException e) {
                System.out.println("Invalid Input: " + e.getMessage());
                AuditLogger.logError(e);
            }
            catch (NullPointerException e) {
                System.out.println("Not Found: " + e.getMessage());
                AuditLogger.logError(e);
            }
            catch (SecurityException e) {
                System.out.println("Security Issue: " + e.getMessage());
                AuditLogger.logError(e);
            }
            catch (IllegalStateException e) {
                System.out.println("Policy Violation: " + e.getMessage());
                AuditLogger.logError(e);
            }
            finally {
                AuditLogger.log("Attempt finished for UID=" + req.uid + ", Asset=" + req.assetId);
                
            }
        }
    }
}