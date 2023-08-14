import { useContext } from "react"
import { CategoryList } from "../../components/categoryList/CategoryList"
import { CoworkingRecommendation } from "../../components/coworkingRecommendation/CoworkingRecommendation"
import { Search } from "../../components/search/Search"
import { SearchResult } from "../../components/searchResult/SearchResult"
import AppContext from "../../context/AppContext"
import SocialMediaSharing from "../../components/socialMediaSharing/SocialMediaSharing"
import iconoLogo from "../../assets/logo1.png";
import "./home.css"

export const Home = () => {

  const { showResults } = useContext(AppContext);

  return (
    <>
      <div className="home">
        <Search />
        <CategoryList />
        { showResults && <SearchResult />}
        { !showResults && <CoworkingRecommendation /> }
      </div>
      <div className="redes">
        Compartir en redes sociales 
        <SocialMediaSharing 
        message="Digital Booking, encuentra tu espacio de trabajo ideal"
        hashtags={["Coworking"]}
        image= {iconoLogo}/>
      </div>
    </>
  )
}
