import { CookiesService, useAuthQuery } from "./imports.ts";

export const token = CookiesService.get("UserToken");
//Field
export const useCategories = () =>
  useAuthQuery({
    queryKey: ["CategoryData"],
    url: "/tech-skills/categories",
  });
//systems
export const useSystems = () =>
  useAuthQuery({
    queryKey: ["SystemsData"],
    url: "/admin/systems/",
  });

export const useTechnologies = () =>
  useAuthQuery({
    queryKey: ["technologiesData"],
    url: "/tech-skills/technologies",
  });
