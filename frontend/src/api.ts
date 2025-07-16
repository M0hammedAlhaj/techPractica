import { CookiesService, useAuthQuery } from "./imports.ts";

export const token = CookiesService.get("UserToken");
//Field
export const useCategories = () =>
  useAuthQuery({
    queryKey: ["CategoryData"],
    url: "/tech-skills/categories",
  });
//category
export const useSystems = () =>
  useAuthQuery({
    queryKey: ["fieldsData"],
    url: "/tech-skills/systems",
  });

export const useTechnologies = () =>
  useAuthQuery({
    queryKey: ["technologiesData"],
    url: "/tech-skills/technologies",
  });
