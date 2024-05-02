CREATE TABLE public.recipee (
                                cooking_instructions varchar(255) NULL,
                                description varchar(255) NULL,
                                "name" varchar(255) NOT NULL,
                                CONSTRAINT recipee_pkey PRIMARY KEY ("name")
);

CREATE TABLE public.ingredient (
                                   name varchar(255) NOT NULL,
                                   quantity varchar(255) NULL,
                                   recipee_name varchar(255) NOT NULL,
                                   CONSTRAINT ingredient_pkey PRIMARY KEY (name, recipee_name),
                                   CONSTRAINT fk_recipee FOREIGN KEY (recipee_name) REFERENCES public.recipee("name")
);