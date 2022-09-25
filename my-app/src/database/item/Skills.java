package database.item;

public enum Skills {
    CARPENTRY {
        @Override
        public String toString() {
            return "Carpentry";
        }
    }, BACKEND {
        @Override
        public String toString() {
            return "Back-end support";
        }
    }, UI {
        @Override
        public String toString() {
            return "UI design";
        }
    }, HARDWARE {
        @Override
        public String toString() {
            return "Hardware support";
        }
    }, EMOTIONAL {
        @Override
        public String toString() {
            return "Emotional support";
        }
    }, WIRING {
        @Override
        public String toString() {
            return "Wiring";
        }
    }, CONSULTATION {
        @Override
        public String toString() {
            return "Consultation";
        }
    }, NOTARY {
        @Override
        public String toString() {
            return "Notary";
        }
    }
}
