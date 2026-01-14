/* Styling based on guidelines from https://brand.colostate.edu/color/ and is all AAA compliant */

import { NavLink as Link } from 'react-router-dom'
import styled from 'styled-components'

export const Nav = styled.nav`
  font-size: clamp(1rem, 2vw, 1.5rem);
  font-family: 'Poppins', sans-serif;
  background: #7E5475; /* Powered Purple */
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1vw 2vw;
  position: relative;
  z-index: 10;
`

export const NavLogo = styled.div`
  color: #FFFFFF;
  font-weight: bold;
  white-space: nowrap;
`

export const MobileIcon = styled.img`
  display: none;
  height: 2rem;
  width: 2rem;
  cursor: pointer;
  filter: brightness(0) invert(1); 

  @media screen and (max-width: 768px) {
    display: block;
  }
`

export const NavMenu = styled.div`
  display: flex;
  align-items: center;
  margin-left: auto;
  gap: 1rem; 

  @media screen and (max-width: 768px) {
    display: none;
  }
`

export const DrawerMenu = styled.div`
  background-color: #7E5475; /* Powered Purple */
  position: fixed;
  top: 0;
  right: 0;
  height: 100vh;
  width: 250px;
  padding: 2rem 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.75rem; 
  transform: ${({ isOpen }) => (isOpen ? 'translateX(0)' : 'translateX(100%)')};
  transition: transform 0.3s ease-in-out;

  @media screen and (min-width: 769px) {
    display: none;
  }
`

export const NavLink = styled(Link)`
  color: #FFFFFF;
  display: flex;
  align-items: center;
  text-decoration: none;
  padding: 0.5rem 1rem; 
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;

  @media screen and (min-width: 769px) {
    padding: 0.7rem 1.2rem;
    margin: 0 0.2rem; 
  }

  &.active {
    color: #CFFC00; /* Energy Green */
  }

  &:hover {
    background-color: #007C91; /* Horsetooth Blue */
    color: #FFFFFF;
  }
`