// Styling based on guidelines from https://brand.colostate.edu/color/ and is all AAA or AA compliant

// Base typographic variables
const baseFontFamily = "'Poppins', sans-serif";
const baseFontSize = '1rem';
const baseLineHeight = 1.5;

// Font size scale (consistent ratio)
const fontSizes = {
  xs: '0.75rem',    // 12px
  sm: '0.875rem',   // 14px
  base: '1rem',     // 16px
  md: '1.125rem',   // 18px
  lg: '1.25rem',    // 20px
  xl: '1.5rem',     // 24px
  '2xl': '1.75rem', // 28px
  '3xl': '2rem',    // 32px
  '4xl': '2.5rem',  // 40px
};

// Font weights
const fontWeights = {
  normal: 400,
  medium: 500,
  bold: 700,
};

// Base styles that can be shared across components
const baseContainerStyle = {
    margin: '0.2vw',
    fontFamily: baseFontFamily,
    borderRadius: '8px',
    boxShadow: '0 4px 8px rgba(0,0,0,0.2)',
}

const baseButtonStyle = {
    fontFamily: baseFontFamily,
    fontSize: fontSizes.md,
    fontWeight: fontWeights.bold,
    textAlign: 'center',
    cursor: 'pointer',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.2)',
}

const baseCardStyle = {
    padding: '20px',
    borderRadius: '8px',
    boxShadow: '0 4px 8px rgba(0,0,0,0.2)',
    margin: '10px 0',
    fontFamily: baseFontFamily,
    fontSize: fontSizes.base,
    border: '1px solid rgba(255,255,255,0.2)',
}

const baseSectionHeadingStyle = {
    borderBottom: '2px solid #FFFFFF',
    paddingBottom: '8px',
    marginBottom: '12px',
    fontFamily: baseFontFamily,
    fontSize: fontSizes.xl,
    fontWeight: fontWeights.bold,
}

const baseInfoBarStyle = {
    display: 'flex',
    justifyContent: 'space-between',
    marginBottom: '20px',
    padding: '10px',
    borderRadius: '6px',
    background: 'rgba(255,255,255,0.1)',
    fontFamily: baseFontFamily,
    fontSize: fontSizes.base,
    border: '1px solid rgba(255,255,255,0.1)',
}

const baseItemStyle = {
    padding: '10px 15px',
    margin: '5px 0',
    borderRadius: '6px',
    fontSize: fontSizes.md,
    fontWeight: fontWeights.bold,
    cursor: 'pointer',
    fontFamily: baseFontFamily,
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
}

// Color styles
export const goldenContainerStyle = {
    ...baseContainerStyle,
    background: '#008FB3', // Horsetooth Blue
    color: '#FFFFFF',
    padding: '0.5vw',
    fontSize: fontSizes.lg,
    border: '1px solid #00BEED', // Lighter blue for border
}

export const grayContainerStyle = {
    ...baseContainerStyle,
    background: '#1E4D2B', // CSU Green
    color: '#FFFFFF',
    padding: '0.2vw',
    fontSize: fontSizes.md,
    border: '1px solid #2A7241', // Lighter green for border
}

export const darkGrayContainerStyle = {
    ...baseContainerStyle,
    background: '#CFFC00', // Energy Green
    color: '#000000',
    padding: '0.2vw',
    fontSize: fontSizes.md,
    border: '1px solid #A3C900', // Darker green for border
}

export const greenContainerStyle = {
    ...baseContainerStyle,
    background: '#82C503', // Lover's Lane
    color: '#000000',
    padding: '0.5vw',
    fontSize: fontSizes.md,
    border: '1px solid #6B9F03', // Darker green for border
}

export const redContainerStyle = {
    ...baseContainerStyle,
    background: '#E56A54', // Flower Trial Red
    color: '#FFFFFF',
    padding: '0.5vw',
    fontSize: fontSizes.md,
    border: '1px solid #D04535', // Darker red for border
}

export const yellowContainerStyle = {
    ...baseContainerStyle,
    background: '#FFC038', // Sunshine
    color: '#000000',
    padding: '0.5vw',
    fontSize: fontSizes.md,
    border: '1px solid #E6AA29', // Darker yellow for border
}

// Unassign Worker Styling
export const purpleContainerStyle = {
    ...baseButtonStyle,
    backgroundColor: '#7E5475', // Powered Purple
    color: '#ffffff',
    padding: '0.5rem 1rem',
    border: '2px solid #ffffff',
    fontSize: fontSizes.sm,
}

export const pageStyle = {
    padding: '1vw',
    fontFamily: baseFontFamily,
    fontSize: fontSizes.md
}

// Assign Worker Drop Down Styling
export const dropdownContainerStyle = {
    position: 'relative',
    width: '100%'
};
  
export const dropdownStyle = {
    position: 'absolute',
    zIndex: 10,
    background: '#000000',
    border: '1px solid #555',
    borderRadius: '4px',
    width: '700px',
    maxHeight: '400px',
    overflowY: 'auto',
    boxShadow: '0 4px 8px rgba(0,0,0,0.3)'
};
  
export const dropdownItemStyle = {
    padding: '16px 20px',
    cursor: 'pointer',
    borderBottom: '1px solid #555',
    backgroundColor: '#424242',
    color: '#FFFFFF',
    fontSize: fontSizes.md,
    fontFamily: baseFontFamily,
    transition: 'background-color 0.2s'
};
  
export const overlayStyle = {
    position: 'fixed',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    zIndex: 5
};

// Qualifications Styling
export const qualificationCardStyle = {
    ...baseCardStyle,
    background: '#1E4D2B', // CSU Green
    color: '#FFFFFF',
}

export const qualificationWorkerListStyle = {
    marginTop: '10px',
    padding: '10px',
    borderTop: '1px solid #2A7241', // Lighter CSU Green for border
    fontFamily: baseFontFamily,
}

export const qualificationWorkerHeadingStyle = {
    ...baseSectionHeadingStyle,
    color: '#FFFFFF',
    fontSize: fontSizes.lg,
}

export const noWorkersMessageStyle = {
    fontStyle: 'italic',
    color: '#FFC038', // Sunshine yellow
    padding: '10px',
    fontFamily: baseFontFamily,
    fontSize: fontSizes.base,
}

// Worker Styling
export const workerCardStyle = {
    ...baseCardStyle,
    background: '#1E4D2B', // CSU Green
    color: '#FFFFFF', // White
}

export const workerInfoBarStyle = {
    ...baseInfoBarStyle
}

export const workerInfoLabelStyle = {
    color: '#FFFFFF', // White
    fontWeight: fontWeights.bold,
    fontFamily: baseFontFamily,
    fontSize: fontSizes.base,
}

export const workerSectionHeadingStyle = {
    ...baseSectionHeadingStyle,
    color: '#FFFFFF', // White
    fontSize: fontSizes.lg,
}

export const workerProjectItemStyle = {
    ...baseItemStyle,
    background: '#82C503', // Lover's Lane
    color: '#000000', // Black
    border: '1px solid #6B9F03', // Darker green for border
    fontSize: fontSizes.base,
}

export const workerQualificationItemStyle = {
    ...baseItemStyle,
    background: '#7E5475', // Powered Purple instead of white
    color: '#FFFFFF', // White text for good contrast
    border: '1px solid #9A6B8F', // Lighter purple for border
    fontSize: fontSizes.base,
}

// Project Styling
export const projectCardStyle = {
    ...baseCardStyle,
    background: '#1E4D2B', // CSU Green
    color: '#FFFFFF',
}

export const projectInfoBarStyle = {
    ...baseInfoBarStyle
}

export const projectSectionHeadingStyle = {
    ...baseSectionHeadingStyle,
    color: '#FFFFFF',
    fontSize: fontSizes.lg,
}

export const projectInfoLabelStyle = {
    color: '#FFFFFF', 
    fontWeight: fontWeights.bold,
    fontFamily: baseFontFamily,
    fontSize: fontSizes.base,
}

// Form Styling
export const addWorkerButtonStyle = {
    ...baseButtonStyle,
    background: '#FFC038', // Sunshine
    color: '#000000', // Black
    padding: '12px 20px',
    borderRadius: '6px',
    fontSize: fontSizes.md,
    border: '1px solid #E6AA29', // Darker yellow for border
    fontFamily: baseFontFamily,
    marginBottom: '20px',
}

export const workerFormContainerStyle = {
    ...baseCardStyle,
    background: '#1E4D2B', // CSU Green
}

// Change the heading style for forms to use white text
export const formHeadingStyle = {
    fontSize: fontSizes['2xl'],
    fontWeight: fontWeights.bold, 
    color: '#FFFFFF', // White 
    marginBottom: '20px',
    fontFamily: baseFontFamily,
}

export const formLabelStyle = {
    display: 'block',
    marginBottom: '8px',
    color: '#FFFFFF', // White
    fontFamily: baseFontFamily,
    fontSize: fontSizes.base,
}

export const formInputStyle = {
    width: '100%',
    padding: '10px',
    borderRadius: '4px',
    border: '1px solid #FFFFFF', // White
    backgroundColor: '#f8f8f8',
    color: '#333', // Dark gray
    fontSize: fontSizes.base,
    fontFamily: baseFontFamily,
}

export const formGroupStyle = {
    marginBottom: '20px',
}

export const submitButtonStyle = {
    ...baseButtonStyle,
    backgroundColor: '#7E5475', // Powered Purple
    color: '#ffffff', // White
    padding: '10px 20px',
    borderRadius: '6px',
    border: '1px solid #9A6B8F', // Lighter purple for border
    fontSize: fontSizes.base,
    fontFamily: baseFontFamily,
}

export const messageContainerStyle = {
    padding: '10px',
    marginBottom: '15px',
    borderRadius: '4px',
    fontFamily: baseFontFamily,
    fontSize: fontSizes.base,
}

export const errorMessageStyle = {
    ...messageContainerStyle,
    backgroundColor: 'rgba(229, 106, 84, 0.2)', // Transparent Flower Trial Red
    color: '#FFFFFF', // White
    border: '1px solid #E56A54', // Flower Trial Red
}

export const successMessageStyle = {
    ...messageContainerStyle,
    backgroundColor: 'rgba(130, 197, 3, 0.2)', // Transparent Lover's Lane
    color: '#FFFFFF', // White
    border: '1px solid #82C503', // Lover's Lane
}

// Styles for worker qualification displays
export const matchingQualStyle = {
    color: '#82C503',
    fontWeight: fontWeights.bold,
    display: 'block',
    marginTop: '8px',
    marginBottom: '4px',
    fontFamily: baseFontFamily,
    fontSize: fontSizes.base,
}

export const missingQualStyle = {
    color: '#FFC038',
    fontWeight: fontWeights.bold,
    display: 'block',
    fontFamily: baseFontFamily,
    fontSize: fontSizes.base,
}

export const modalOverlayStyle = {
    position: 'fixed',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: 'rgba(0, 0, 0, 0.7)',
    zIndex: 1000,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  };
  
  export const modalContentStyle = {
    backgroundColor: '#1E4D2B',
    borderRadius: '8px',
    padding: '20px',
    width: '80%',
    maxWidth: '800px',
    maxHeight: '80vh',
    overflowY: 'auto',
    position: 'relative',
    border: '1px solid #2A7241'
  };
  
  export const modalCloseButtonStyle = {
    position: 'absolute',
    right: '10px',
    top: '10px',
    background: 'none',
    border: 'none',
    color: 'white',
    fontSize: '1.2rem',
    cursor: 'pointer'
  };
  
  export const workerHeadingStyle = {
    color: '#FFC038',
    marginBottom: '15px'
  };
  
  export const workerDetailsSectionStyle = {
    display: 'flex',
    flexWrap: 'wrap',
    gap: '30px'
  };
  
  export const workerDetailsPanelStyle = {
    flexBasis: '300px',
    flexGrow: 1
  };
  
  export const workerInfoListStyle = {
    marginLeft: '15px'
  };
  
  export const qualBadgeStyle = {
    backgroundColor: '#4CAF50',
    color: 'white',
    padding: '5px 10px',
    borderRadius: '4px',
    cursor: 'pointer'
  };
  
  export const qualBadgeListStyle = {
    listStyleType: 'none',
    padding: 0,
    margin: 0,
    display: 'flex',
    flexWrap: 'wrap',
    gap: '8px'
  };
  
  export const projectListItemStyle = {
    backgroundColor: '#008FB3',
    color: 'white',
    padding: '8px 15px',
    marginBottom: '10px',
    borderRadius: '4px',
    cursor: 'pointer'
  };
  
  export const viewDetailsButtonStyle = {
    ...submitButtonStyle,
    backgroundColor: '#008FB3',
    color: '#ffffff',
    border: '2px solid #ffffff',
    padding: '5px 10px',
    fontSize: '0.9rem',
    marginLeft: '10px'
  };
  
  export const workerListItemStyle = {
    marginBottom: '10px',
    display: 'flex',
    alignItems: 'center'
  };
  
  export const workerNameLinkStyle = {
    cursor: 'pointer',
    color: 'white',
    marginRight: '1rem'
  };
  
  export const buttonGroupStyle = {
    display: 'flex',
    gap: '10px'
  };