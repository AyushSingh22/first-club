CREATE DATABASE first_club;

USE first_club;

CREATE TABLE membership_plans (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    plan_name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    plan_type ENUM('MONTHLY', 'YEARLY') NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    duration_days INT NOT NULL,
    discount_percentage DECIMAL(5,2),
    free_delivery BOOLEAN NOT NULL DEFAULT FALSE,
    priority_support BOOLEAN NOT NULL DEFAULT FALSE,
    early_access BOOLEAN NOT NULL DEFAULT FALSE,
    perks JSON,
    status ENUM('ACTIVE', 'INACTIVE', 'DISCONTINUED', 'COMING_SOON') NOT NULL,
    sort_order INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE membership_tiers (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tier_name VARCHAR(255) NOT NULL UNIQUE,
    tier_level ENUM('SILVER', 'GOLD', 'PLATINUM') NOT NULL,
    description TEXT,
    min_spend_amount DECIMAL(10,2) NOT NULL,
    max_spend_amount DECIMAL(10,2),
    additional_discount DECIMAL(5,2),
    cashback_percentage DECIMAL(5,2),
    free_delivery_threshold DECIMAL(10,2),
    benefits JSON,
    upgrade_criteria JSON,
    status ENUM('ACTIVE', 'INACTIVE', 'DISCONTINUED') NOT NULL,
    sort_order INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    status ENUM('ACTIVE', 'INACTIVE', 'BLOCKED', 'PENDING_VERIFICATION') NOT NULL,
    total_spent DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    phone_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE user_memberships (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    membership_plan_id BIGINT NOT NULL,
    membership_tier_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    next_billing_date DATE,
    status ENUM('ACTIVE', 'EXPIRED', 'CANCELLED', 'SUSPENDED', 'PENDING_PAYMENT') NOT NULL,
    amount_paid DECIMAL(10,2) NOT NULL,
    auto_renewal BOOLEAN NOT NULL DEFAULT TRUE,
    payment_method_id VARCHAR(255),
    subscription_id VARCHAR(255),
    cancelled_at DATETIME,
    cancelled_reason TEXT,
    upgraded_from_id BIGINT,
    downgraded_from_id BIGINT,
    prorated_amount DECIMAL(10,2),

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_membership_plan FOREIGN KEY (membership_plan_id) REFERENCES membership_plans(id),
    CONSTRAINT fk_membership_tier FOREIGN KEY (membership_tier_id) REFERENCES membership_tiers(id)
);