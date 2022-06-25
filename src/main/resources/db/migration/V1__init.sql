use vendor;

CREATE TABLE IF NOT EXISTS `vendor`.`mc` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gst` VARCHAR(20) NOT NULL,
  `pan` VARCHAR(20) NULL,
  `business_name` VARCHAR(256) NOT NULL,
  `listing_name` VARCHAR(256) NOT NULL,
  `address` JSON NOT NULL,
  `poc` JSON NOT NULL,
  `fssai` INT NULL,
  `created_on` DATETIME NOT NULL,
  `updated_on` DATETIME NOT NULL,
  `created_by` integer NOT NULL,
  `updated_by` integer NOT NULL,
  `active` TINYINT NULL default 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `gst` (`gst` ASC) )
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `vendor`.`supplier` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mc_id` INT NOT NULL,
  `gst` VARCHAR(20) NOT NULL,
  `pan` VARCHAR(20) NULL,
  `business_name` VARCHAR(256) NOT NULL,
  `listing_name` VARCHAR(256) NOT NULL,
  `address` JSON NOT NULL,
  `poc` JSON NOT NULL,
  `bank_account_name` VARCHAR(64)  NULL,
  `bank_account_number` VARCHAR(64)  NULL,
  `bank_ifsc`  VARCHAR(64)  NULL,
  `bank_address`  VARCHAR(512)  NULL,
  `supplier_type` VARCHAR(20) NULL,
  `credit_days` INT NULL,
  `sor` tinyint ,
  `msme_certificate` tinyint,
  `msme_certificate_number` varchar(20),
  `rtv_policy` varchar(20),
  `supplier_poc` JSON NOT NULL,
  `fssai` INT NULL,
  `created_on` DATETIME NOT NULL,
  `updated_on` DATETIME NOT NULL,
  `created_by` integer NOT NULL,
  `updated_by` integer NOT NULL,
  `active` TINYINT NULL default 1,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`mc_id`) REFERENCES mc(`id`),
  UNIQUE INDEX `gst` (`gst` ASC) )
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `vendor`.`brand` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mc_id` INT NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `slug` VARCHAR(64) NULL,
  `seo_meta_description` VARCHAR(256) NOT NULL,
  `seo_meta_keywords` VARCHAR(256) NOT NULL,
  `seo_page_title` VARCHAR(256) NOT NULL,
  `internal_name` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`mc_id`) REFERENCES mc(`id`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `vendorconnect`.`product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `brand_id` INT NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `pack_type` VARCHAR(64) NOT NULL,
  `slug` VARCHAR(64) NULL,
  `description` VARCHAR(256) NOT NULL,
  `weight` VARCHAR(20) NOT NULL,
  `normalized_weight` VARCHAR(20) NOT NULL,
  `product_type` VARCHAR(20) NOT NULL,
  `department` VARCHAR(20) NOT NULL,
  `tlc_id` VARCHAR(20) NOT NULL,
  `is_combo` tinyint,
  `category_id` VARCHAR(20) NOT NULL,
  `product_sub_type` VARCHAR(20) NOT NULL,
  `shelf_life` VARCHAR(20) NOT NULL,
  `hsn_code` VARCHAR(20) NOT NULL,
  `length` VARCHAR(20) NOT NULL,
  `depth` VARCHAR(20) NOT NULL,
  `height` VARCHAR(20) NOT NULL,
  `created_on` DATETIME NOT NULL,
  `updated_on` DATETIME NOT NULL,
  `created_by` integer NOT NULL,
  `updated_by` integer NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`brand_id`) REFERENCES brand(`id`))
ENGINE = InnoDB;

CREATE TABLE acl_sid (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	principal BOOLEAN NOT NULL,
	sid VARCHAR(100) NOT NULL,
	UNIQUE KEY unique_acl_sid (sid, principal)
) ENGINE=InnoDB;

CREATE TABLE acl_class (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	class VARCHAR(255) NOT NULL,
	class_id_type varchar(255),
	UNIQUE KEY uk_acl_class (class)
) ENGINE=InnoDB;

CREATE TABLE acl_object_identity (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_id_class BIGINT UNSIGNED NOT NULL,
	object_id_identity VARCHAR(36) NOT NULL,
	parent_object BIGINT UNSIGNED,
	owner_sid BIGINT UNSIGNED,
	entries_inheriting BOOLEAN NOT NULL,
	UNIQUE KEY uk_acl_object_identity (object_id_class, object_id_identity),
	CONSTRAINT fk_acl_object_identity_parent FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
	CONSTRAINT fk_acl_object_identity_class FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
	CONSTRAINT fk_acl_object_identity_owner FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;

CREATE TABLE acl_entry (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	acl_object_identity BIGINT UNSIGNED NOT NULL,
	ace_order INTEGER NOT NULL,
	sid BIGINT UNSIGNED NOT NULL,
	mask INTEGER UNSIGNED NOT NULL,
	granting BOOLEAN NOT NULL,
	audit_success BOOLEAN NOT NULL,
	audit_failure BOOLEAN NOT NULL,
	UNIQUE KEY unique_acl_entry (acl_object_identity, ace_order),
	CONSTRAINT fk_acl_entry_object FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
	CONSTRAINT fk_acl_entry_acl FOREIGN KEY (sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;

INSERT INTO acl_sid (id, principal, sid)
VALUES
(1, 1, 's@g.com'),
(2, 0, 'ROLE_ADMIN');

INSERT INTO acl_class (id, class,class_id_type)
VALUES
(1, 'com.binimise.vendor.models.MC','com.binimise.vendor.models.MC');

INSERT INTO acl_object_identity
(id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES
(1, 1, 1, NULL, 1, 1);

INSERT INTO acl_entry
(id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES
(1, 1, 0, 1, 2, 1, 0, 0);

INSERT INTO acl_entry
(id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES
(2, 1, 1, 2, 16, 1, 0, 0);

CREATE TABLE product_fc_mapping (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	product_id BIGINT UNSIGNED NOT NULL,
	supplier_id INTEGER NOT NULL,
	fc_id INTEGER UNSIGNED NOT NULL,
	CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES product (id),
	CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES supplier (id),
	CONSTRAINT fk_fc_id FOREIGN KEY (fc_id) REFERENCES fc (id)
) ENGINE=InnoDB;


CREATE TABLE fc (
	id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fc_id INTEGER UNSIGNED NOT NULL,
	dc_id INTEGER UNSIGNED NOT NULL,
	name varchar(64) NULL
) ENGINE=InnoDB;

CREATE TABLE product_fc_mapping (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`product_id` BIGINT UNSIGNED NOT NULL,
	`supplier_id` INTEGER NOT NULL,
	`fc_id` INTEGER UNSIGNED ,
	`dc_id` INTEGER UNSIGNED ,
    `created_on` DATETIME NOT NULL,
    `updated_on` DATETIME NOT NULL,
    `created_by` integer NOT NULL,
    `updated_by` integer NOT NULL
) ENGINE=InnoDB;



