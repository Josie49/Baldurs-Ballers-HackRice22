USE [HackRice12]
GO

/****** Object:  Table [dbo].[ASSIGNMENTS]    Script Date: 9/24/2022 12:06:01 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ASSIGNMENTS](
	[employeeID] [bigint] NOT NULL,
	[jobID] [bigint] NOT NULL,
	[startTime] [smallint] NOT NULL
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[EMPLOYEE]    Script Date: 9/24/2022 12:06:17 PM ******/
CREATE TABLE [dbo].[EMPLOYEE](
	[employeeID] [bigint] NOT NULL,
	[phoneNumber] [nchar](11) NOT NULL,
	[startingAddress1] [varchar](max) NOT NULL,
	[startingAddress2] [varchar](max) NULL,
	[startingCity] [varchar](max) NOT NULL,
	[startingState] [nchar](2) NOT NULL,
	[startingZip] [nchar](5) NOT NULL,
	[curAddress1] [varchar](max) NULL,
	[curAddress2] [varchar](max) NULL,
	[curCity] [varchar](max) NULL,
	[curState] [nchar](2) NULL,
	[curZip] [nchar](5) NULL,
	[availability] [bit] NOT NULL,
	[availability1] [bit] NOT NULL,
	[availability2] [bit] NOT NULL,
	[availability3] [bit] NOT NULL,
	[availability4] [bit] NOT NULL,
	[availability5] [bit] NOT NULL,
	[availability6] [bit] NOT NULL,
	[availability7] [bit] NOT NULL,
	[availability8] [bit] NOT NULL,
	[availability9] [bit] NOT NULL,
	[availability10] [bit] NOT NULL,
	[availability11] [bit] NOT NULL,
	[availability12] [bit] NOT NULL,
	[availability13] [bit] NOT NULL,
	[availability14] [bit] NOT NULL,
	[availability15] [bit] NOT NULL,
	[availability16] [bit] NOT NULL,
	[availability17] [bit] NOT NULL,
	[availability18] [bit] NOT NULL,
	[availability19] [bit] NOT NULL,
	[availability20] [bit] NOT NULL,
	[availability21] [bit] NOT NULL,
	[availability22] [bit] NOT NULL,
	[availability23] [bit] NOT NULL,
 CONSTRAINT [PK_EMPLOYEE] PRIMARY KEY CLUSTERED 
(
	[employeeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

/****** Object:  Table [dbo].[EMPLOYEE_SKILLS]    Script Date: 9/24/2022 12:06:55 PM ******/
CREATE TABLE [dbo].[EMPLOYEE_SKILLS](
	[employeeID] [bigint] NOT NULL,
	[skillID] [int] NOT NULL
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[JOB]    Script Date: 9/24/2022 12:07:40 PM ******/
CREATE TABLE [dbo].[JOB](
	[jobID] [bigint] NOT NULL,
	[locationAddress1] [varchar](max) NOT NULL,
	[locationAddress2] [varchar](max) NULL,
	[locationCity] [varchar](max) NOT NULL,
	[locationState] [nchar](2) NOT NULL,
	[locationZip] [nchar](5) NOT NULL,
	[hours] [smallint] NOT NULL,
	[details] [varchar](max) NULL,
	[completion] [bit] NOT NULL,
 CONSTRAINT [PK_JOB] PRIMARY KEY CLUSTERED 
(
	[jobID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

/****** Object:  Table [dbo].[JOB_SKILLS]    Script Date: 9/24/2022 12:08:05 PM ******/
CREATE TABLE [dbo].[JOB_SKILLS](
	[jobID] [bigint] NOT NULL,
	[skillID] [int] NOT NULL
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[SKILLS]    Script Date: 9/24/2022 12:08:21 PM ******/
CREATE TABLE [dbo].[SKILLS](
	[skillID] [int] NOT NULL,
	[skillName] [varchar](max) NOT NULL,
 CONSTRAINT [PK_SKILLS] PRIMARY KEY CLUSTERED 
(
	[skillID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO