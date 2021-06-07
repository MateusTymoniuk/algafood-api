CREATE TABLE restaurante_forma_pagamento (
	restaurante_id BIGINT NOT NULL,
	forma_pagamento_id BIGINT NOT NULL,
	PRIMARY KEY (restaurante_id, forma_pagamento_id),
	CONSTRAINT fk_restaurante_forma_pagamento_restaurante_id FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
	CONSTRAINT fk_restaurante_forma_pagamento_forma_pagamento_id FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id)
) engine = InnoDB default charset = UTF8MB4;