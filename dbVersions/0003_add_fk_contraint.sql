--2 NF database normalisation in membership_tiers with membership_plan_id

ALTER TABLE membership_tiers
ADD CONSTRAINT fk_membership_tiers_plan
FOREIGN KEY (membership_plan_id) REFERENCES membership_plans(id)
ON DELETE SET NULL
ON UPDATE CASCADE;