import React, { useEffect, useState, useMemo } from "react";
import "./Categories.css";
import Pagination from "../pagination/pagination";
import { CardCategoryAdmin } from "../cardCategoryAdmin/cardCategoryAdmin";
import { Loading } from "../loading/Loading";
import * as categoryService from "../../services/categoryServices";

let PageSize = 6;
const Categories = () => {
  const [categories, setCategories] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    categoryService
      .categoryAll()
      .then((response) => {
        return response;
      })
      .then((category) => {
        setCategories(category);
      });
  }, []);

  const showCategories = useMemo(() => {
    const firstPageIndex = (currentPage - 1) * PageSize;
    const lastPageIndex = firstPageIndex + PageSize;
    return categories.slice(firstPageIndex, lastPageIndex);
  }, [currentPage, categories]);

  return (
    <div className="products-paginator">
      {categories.length >= 1 ? (
        <>
          <div className="categories-container">
            {showCategories.map((cat) => {
              return (
                <CardCategoryAdmin
                  key={cat.idCategory}
                  idCategory={cat.idCategory}
                  image={cat.image}
                  name={cat.name}
                  description={cat.description}
                  results={cat.results}
                  onDelete={() => {
                    setCategories(() => {
                      return categories.filter(
                        (ctg) => ctg.idCategory != cat.idCategory
                      );
                    });
                  }}
                />
              );
            })}
          </div>
        </>
      ) : (
        <div className="loading-container">
          <Loading />
        </div>
      )}

      <Pagination
        className="pagination-bar"
        currentPage={currentPage}
        totalCount={categories.length}
        pageSize={PageSize}
        onPageChange={(page) => setCurrentPage(page)}
      />
    </div>
  );
};

export default Categories;
