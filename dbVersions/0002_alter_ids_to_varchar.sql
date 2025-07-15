-- Alter primary key columns to VARCHAR(36)
ALTER TABLE membership_plans MODIFY COLUMN id VARCHAR(36) NOT NULL;
ALTER TABLE membership_tiers MODIFY COLUMN id VARCHAR(36) NOT NULL;
ALTER TABLE users MODIFY COLUMN id VARCHAR(36) NOT NULL;
ALTER TABLE user_memberships MODIFY COLUMN id VARCHAR(36) NOT NULL;

-- Alter foreign key columns to VARCHAR(36)
ALTER TABLE user_memberships MODIFY COLUMN user_id VARCHAR(36) NOT NULL;
ALTER TABLE user_memberships MODIFY COLUMN membership_plan_id VARCHAR(36) NOT NULL;
ALTER TABLE user_memberships MODIFY COLUMN membership_tier_id VARCHAR(36) NOT NULL;
ALTER TABLE user_memberships MODIFY COLUMN upgraded_from_id VARCHAR(36);
ALTER TABLE user_memberships MODIFY COLUMN downgraded_from_id VARCHAR(36);